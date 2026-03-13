package com.myportfolio.my_portfolio_backend.interceptor;

import com.myportfolio.my_portfolio_backend.config.PortfolioScheduleProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class CalendarInterceptor implements HandlerInterceptor {

    private final PortfolioScheduleProperties scheduleProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           @Nullable ModelAndView modelAndView) {

        if (modelAndView == null) return;

        String viewName = modelAndView.getViewName();
        if (viewName != null && viewName.startsWith("redirect:")) return;

        LocalTime now = LocalTime.now();
        LocalTime open = scheduleProperties.getOpen();
        LocalTime close = scheduleProperties.getClose();

        boolean calculatedWithinSchedule = !now.isBefore(open) && !now.isAfter(close);

        if (!modelAndView.getModel().containsKey("withinSchedule")) {
            modelAndView.addObject("withinSchedule", calculatedWithinSchedule);
        }

        if (!modelAndView.getModel().containsKey("opentime")) {
            modelAndView.addObject("opentime", open);
        }

        if (!modelAndView.getModel().containsKey("closetime")) {
            modelAndView.addObject("closetime", close);
        }

        if (!modelAndView.getModel().containsKey("scheduleMessage")) {
            if (calculatedWithinSchedule) {
                modelAndView.addObject(
                        "scheduleMessage",
                        "Bienvenido a nuestra página para realizar tu portfolio. Nuestro horario es de 8:00 a 15:00."
                );
            } else {
                modelAndView.addObject(
                        "scheduleMessage",
                        "El horario para realizar el portfolio es de 8:00 a 15:00, estamos descansando. ¡Vuelve mañana!"
                );
            }
        }
    }
}