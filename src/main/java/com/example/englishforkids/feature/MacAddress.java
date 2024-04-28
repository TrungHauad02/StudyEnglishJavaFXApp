package com.example.englishforkids.feature;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MacAddress {
    public static String getMacAddress(){
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String result = "";
        NetworkInterface ni = null;
        try {
            ni = NetworkInterface.getByInetAddress(localHost);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        try {
            byte[] macAddress = ni.getHardwareAddress();
            if (macAddress != null) {
                String[] hexadecimalFormat = new String[macAddress.length];
                for (int i = 0; i < macAddress.length; i++) {
                    hexadecimalFormat[i] = String.format("%02X", macAddress[i]);
                }
                result = String.join("-", hexadecimalFormat);
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
