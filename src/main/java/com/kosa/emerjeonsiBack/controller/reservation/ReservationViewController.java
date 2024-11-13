package com.kosa.emerjeonsiBack.controller.reservation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationViewController {
    @GetMapping("/reservationDetail")
    public String viewReservationDetail(@RequestParam("exhibitionNo") int exhibitionNo,
                                        @RequestParam("reservationPrice") int reservationPrice,
                                        @RequestParam("reservationQuantity") int reservationQuantity,
                                        Model model) {
        int totalPrice = reservationPrice * reservationQuantity;
        model.addAttribute("exhibitionNo", exhibitionNo);
        model.addAttribute("reservationPrice", reservationPrice);
        model.addAttribute("reservationQuantity", reservationQuantity);
        model.addAttribute("totalPrice", totalPrice);
        return "reservationDetail";
    }
}
