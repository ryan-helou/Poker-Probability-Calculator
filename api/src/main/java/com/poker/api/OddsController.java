package com.poker.api;

import com.poker.api.model.OddsResponse;
import com.poker.engine.Round;
import com.poker.engine.OddsResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/odds")
@CrossOrigin(origins = "http://localhost:5173")
public class OddsController {

    @GetMapping
    public OddsResponse compute(
            @RequestParam int value1,
            @RequestParam String suit1,
            @RequestParam int value2,
            @RequestParam String suit2,
            @RequestParam int players,
            @RequestParam int iterations
    ) {
        OddsResult r = Round.calculateOdds(
                value1, suit1,
                value2, suit2,
                players, iterations
        );
        return new OddsResponse(r.win, r.tie, r.lose);
    }
}
