package ASE.deliverySpring.utils;

import ASE.deliverySpring.entity.UserAccount;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// util class used for manage JWT
@Component
public class JWT {
    public String genJWT(UserAccount userAccount){
        Map<String, Object> claim = new HashMap<>();
        claim.put("Account",userAccount.getAccount());
        claim.put("Password",userAccount.getPassword());
        claim.put("Role",userAccount.getRole());

        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256,"ASEDelivery")   //set signature algorithm
                                            .setClaims(claim)       //set the claim
                                            .setExpiration(new Date(System.currentTimeMillis() + 3600*1000))    //set expiration date
                                            .compact();     // convert to string
        return token;
    }
}
