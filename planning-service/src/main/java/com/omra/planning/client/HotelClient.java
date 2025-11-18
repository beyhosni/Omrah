package com.omra.planning.client;

import com.omra.planning.dto.HotelOption;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "hotel-service", url = "${services.hotel.url}")
public interface HotelClient {

    @GetMapping("/api/hotels/search")
    List<HotelOption> searchHotels(@RequestParam String city);
}
