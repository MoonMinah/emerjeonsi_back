package com.kosa.emerjeonsiBack.controller;

import com.kosa.emerjeonsiBack.dto.Exhibition;
import com.kosa.emerjeonsiBack.dto.Payments;
import com.kosa.emerjeonsiBack.dto.Reservation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
public class ExamController {

    @GetMapping({"", "/demo"})
    public ModelAndView demo() {

        return new ModelAndView("demo");
    }

    @GetMapping("/exhibtionDetail")
    public ModelAndView exhibtionDetail() {

        return new ModelAndView("exhibtionDetail");
    }



    /**
     * exhibitionDetail RestApi
     * @return
     */
    @GetMapping("/api/exhibitionDetail")
    public ResponseEntity<Exhibition> exhibition() {
        return ResponseEntity.ok(new Exhibition(1, "다이노베이", "설명","2024.10.11", "2024.10.31", 1000));

    }


    /**
     * payment RestAPI
     * @return
     */
    @GetMapping("/api/payment")
    public ResponseEntity<Payments> payment() {

        return ResponseEntity.ok(new Payments(1 , "어린이", 1000, 2));

    }

    @PostMapping("/paymentOk")
    public ResponseEntity<Reservation> paymentOk(@RequestParam("userNo") String userNo, @RequestParam("exhibitionNo") String exhibitionNo,
                                                 @RequestParam("reservationNo") String reservationNo,
                                                 @RequestParam("reservationPrice") String reservationPrice, @RequestParam("reservationQuantity") String reservationQuantity) {
        int user = Integer.parseInt(userNo);
        int exhibition = Integer.parseInt(exhibitionNo);
        int reservation = Integer.parseInt(reservationNo);
        int price = Integer.parseInt(reservationPrice);
        int quantity = Integer.parseInt(reservationQuantity);


        return ResponseEntity.ok(new Reservation(user, exhibition, reservation, price, quantity));
    }
}
