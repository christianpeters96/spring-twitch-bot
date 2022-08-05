package de.sharpadogge.twitchbot.config.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import de.sharpadogge.twitchbot.permission.Secured;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if( handler instanceof HandlerMethod ) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            Secured secured = handlerMethod.getMethod().getAnnotation(Secured.class);
            if (secured != null) {
                if (secured.value().length > 0) {
                    
                }
            }
		}

        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}