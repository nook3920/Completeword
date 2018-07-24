package com.nook.completeword;

public class LangCheck {
    public static String getDBByKeyTH(String str){
        if(str.length() > 0) {
            str = str.substring(0, 1);
        }else{
            str = "-";
        }
        switch (str) {
            case "ก":
                return "TH00";
            case "ข":
                return "TH01";
            case "ฃ":
                return "TH02";
            case "ค":
                return "TH03";
            case "ฅ":
                return "TH04";
            case "ฆ":
                return "TH05";
            case "ง":
                return "TH06";
            case "จ":
                return "TH07";
            case "ฉ":
                return "TH08";
            case "ช":
                return "TH09";
            case "ซ":
                return "TH10";
            case "ฌ":
                return "TH11";
            case "ญ":
                return "TH12";
            case "ฎ":
                return "TH13";
            case "ฏ":
                return "TH14";
            case "ฐ":
                return "TH15";
            case "ฑ":
                return "TH16";
            case "ฒ":
                return "TH17";
            case "ณ":
                return "TH18";
            case "ด":
                return "TH19";
            case "ต":
                return "TH20";
            case "ถ":
                return "TH21";
            case "ท":
                return "TH22";
            case "ธ":
                return "TH23";
            case "น":
                return "TH24";
            case "บ":
                return "TH25";
            case "ป":
                return "TH26";
            case "ผ":
                return "TH27";
            case "ฝ":
                return "TH28";
            case "พ":
                return "TH29";
            case "ฟ":
                return "TH30";
            case "ภ":
                return "TH31";
            case "ม":
                return "TH32";
            case "ย":
                return "TH33";
            case "ร":
                return "TH34";
            case "ฤ":
                return "TH35";
            case "ล":
                return "TH36";
            case "ฦ":
                return "TH37";
            case "ว":
                return "TH38";
            case "ศ":
                return "TH39";
            case "ษ":
                return "TH40";
            case "ส":
                return "TH41";
            case "ห":
                return "TH42";
            case "ฬ":
                return "TH43";
            case "อ":
                return "TH44";
            case "ฮ":
                return "TH45";
            case "ฯ":
                return "TH46";
            case "เ":
                return "TH47";
            case "แ":
                return "TH48";
            case "โ":
                return "TH49";
            case "ใ":
                return "TH50";
            case "ไ":
                return "TH51";
            default:
                return "TH52";
        }
    }
}
