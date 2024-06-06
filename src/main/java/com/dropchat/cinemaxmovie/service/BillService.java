package com.dropchat.cinemaxmovie.service;

import com.dropchat.cinemaxmovie.configuration.ApplicationConfig;
import com.dropchat.cinemaxmovie.converter.request.BillRequest;
import com.dropchat.cinemaxmovie.converter.response.MessageResponse;
import com.dropchat.cinemaxmovie.entity.Bill;
import com.dropchat.cinemaxmovie.entity.BillFood;
import com.dropchat.cinemaxmovie.entity.BillTicket;
import com.dropchat.cinemaxmovie.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillService {
    private final BillStatusRepository billStatusRepository;
    private final BillTicketRepository billTicketRepository;
    private final PromotionRepository promotionRepository;
    private final BillFoodRepository billFoodRepository;
    private final TicketRepository ticketRepository;
    private final FoodRepository foodRepository;
    private final BillRepository billRepository;
    private final RestTemplate restTemplate;
    private final ApplicationConfig config;
    private final ModelMapper modelMapper;

    private String generateCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;
        return String.valueOf(randomNumber);
    }

    public String addNew(BillRequest newBill, String request) {
        newBill.setTradingCode(generateCode());
        newBill.setCreateTime(new Date());
        newBill.setUpdateTime(new Date());
        newBill.setBillStatus(billStatusRepository.findById(2).orElse(null));
        var bill = modelMapper.map(newBill, Bill.class);
        bill.setPromotion(promotionRepository.findById(newBill.getPromotion()).orElseThrow());
        billRepository.save(bill);

        var check = billRepository.findByTradingCode(newBill.getTradingCode())
                .orElseThrow(() -> new RuntimeException("Data not found"));
        if (check.getUser() == null
                || check.getBillStatus() == null) {
            billRepository.delete(check);
            log.error("CustomerId and BillStatusId must not be null !");
        }

        // Choose Movie and food
        newBill.getFoodItems().forEach(x -> {
            var quantity = x.getQuantity();
            var food = x.getFood();
            BillFood newList = new BillFood(quantity, check, foodRepository.findById(food).orElseThrow());
            billFoodRepository.save(newList);
        });

        newBill.getTicketItems().forEach(x -> {
            var quantity = x.getQuantity();
            var ticket = x.getTicket();
            BillTicket newList = new BillTicket(quantity, check, ticketRepository.findById(ticket).orElseThrow());
            billTicketRepository.save(newList);
        });

        countMoney(check);
        billRepository.save(check);

        String url = request + "/api/v1/payment/submitOrder/" + check.getTradingCode();
        return restTemplate.postForObject(url, null, String.class);
    }

    private void countMoney(Bill request) {
        var totalFoodPrice = billFoodRepository.findAll()
                .stream()
                .filter(x -> x.getBill().equals(request))
                .mapToDouble(x -> x.getQuantity() * x.getFood().getPrice())
                .sum();
        var totalTicketPrice = billTicketRepository.findAll()
                .stream()
                .filter(x -> x.getBill().equals(request))
                .mapToDouble(x -> x.getQuantity() * x.getTicket().getPriceTicket())
                .sum();
        if (request.getPromotion() == null) {
            request.setTotalMoney(totalTicketPrice + totalFoodPrice);
        } else {
            request.setTotalMoney((totalTicketPrice + totalFoodPrice) * (1 - request.getPromotion().getPercent() / 100.0));
        }
    }

    public String remake(BillRequest remakeBill, String request) {
        var current = billRepository.findById((long) remakeBill.getId())
                .orElseThrow(() -> new RuntimeException("Data not found"));

        if (remakeBill.getFoodItems() != null) {
            for (var foodItem : remakeBill.getFoodItems()) {
                var quantity = foodItem.getQuantity();
                var food = foodItem.getFood();
                if (quantity == 0) {
                    if (billFoodRepository
                            .findBillFoodByBillAndFood(current, foodRepository.findById(food).orElseThrow())
                            .isEmpty()) continue;
                    // If quantity is 0, delete the corresponding BillFood
                    var currentBillFood = billFoodRepository
                            .findBillFoodByBillAndFood(current, foodRepository.findById(food).orElseThrow())
                            .orElseThrow();
                    billFoodRepository.delete(currentBillFood);
                } else {
                    // Fetch the corresponding BillFood
                    BillFood billFood = billFoodRepository
                            .findBillFoodByBillAndFood(current, foodRepository.findById(food).orElseThrow())
                            .orElse(new BillFood(quantity, current, foodRepository.findById(food).orElseThrow()));

                    // Update quantity and save the BillFood
                    billFood.setQuantity(quantity);
                    billFoodRepository.save(billFood);
                }
            }
        }
        if (remakeBill.getTicketItems() != null) {
            for (var ticketItem : remakeBill.getTicketItems()) {
                var quantity = ticketItem.getQuantity();
                var ticket = ticketItem.getTicket();
                if (quantity == 0) {
                    if (billTicketRepository
                            .findBillTicketByBillAndTicket(current, ticketRepository.findById(ticket).orElseThrow())
                            .isEmpty()) continue;
                    var currentBillTicket = billTicketRepository
                            .findBillTicketByBillAndTicket(current, ticketRepository.findById(ticket).orElseThrow())
                            .orElseThrow();
                    billTicketRepository.delete(currentBillTicket);
                } else {
                    BillTicket billTicket = billTicketRepository.findBillTicketByBillAndTicket(current, ticketRepository.findById(ticket).orElseThrow())
                            .orElse(new BillTicket(quantity, current, ticketRepository.findById(ticket).orElseThrow()));

                    billTicket.setQuantity(quantity);
                    billTicketRepository.save(billTicket);
                }
            }
        }
        countMoney(current);
        remakeBill.setTotalMoney(current.getTotalMoney());

        remakeBill.setUpdateTime(new Date());
        BeanUtils.copyProperties(remakeBill, current, config.getNullPropertyNames(remakeBill));
        billRepository.save(current);
        String url = request + "/api/v1/payment/submitOrder/" + current.getTradingCode();
        return restTemplate.postForObject(url, null, String.class);
    }

    public MessageResponse delete(String name) {
        var current = billRepository.findByTradingCode(name)
                .orElseThrow(() -> new RuntimeException("Data not found"));
        current.setActive(false);
        return new MessageResponse("Delete success !");
    }
}
