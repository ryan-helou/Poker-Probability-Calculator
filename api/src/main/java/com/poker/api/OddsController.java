package com.poker.api;

import com.poker.api.model.OddsRequest;
import com.poker.api.model.OddsResponse;
import com.poker.engine.Round;
import com.poker.engine.OddsResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/odds")
@CrossOrigin(origins = "http://localhost:5173") // dev: allow Vite UI
public class OddsController {

    @PostMapping
    public OddsResponse compute(@RequestBody OddsRequest req) {
        OddsResult r = Round.calculateOdds(
                req.value1, req.suit1,
                req.value2, req.suit2,
                req.players, req.iterations
        );
        return new OddsResponse(r.win, r.tie, r.lose);
    }
}
