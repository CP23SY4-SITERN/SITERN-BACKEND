package com.example.siternbackend.user.Decoder;

import org.json.JSONObject;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenDecoder {

    public UserDetails decodeToken(String accessToken) {
        // ทำการ decode token และแปลงข้อมูลให้อยู่ในรูปแบบของ UserDetails
        JSONObject jsonPayload = decodePayload(accessToken);
        UserDetails userDetails = buildUserDetails(jsonPayload);
        return userDetails;
    }

    private JSONObject decodePayload(String accessToken) {
        // ทำการ decode payload จาก token
        // คำนึงถึงการที่จะใช้ตัว decode ของคุณ
        // โดยสร้าง JSONObject จากข้อมูลที่ decode ได้
        return new JSONObject();
    }

    private UserDetails buildUserDetails(JSONObject jsonPayload) {
        // สร้าง UserDetails จากข้อมูลที่ decode ได้
        // คำนึงถึงการ mapping ข้อมูลกับ UserDetails ของคุณ
        // และเรียกใช้ข้อมูลที่ต้องการ
        return null;
    }
}

