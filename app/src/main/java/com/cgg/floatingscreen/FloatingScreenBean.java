package com.cgg.floatingscreen;

public class FloatingScreenBean {

    private TextBean text;

    public TextBean getText() {
        return text;
    }

    public void setText(TextBean text) {
        this.text = text;
    }

    public static class TextBean {

        private String type;
        private String name;
        private String img;
        private String icon_img;
        private String title;
        private String gift_img;
        private String gift_name;
        private String gift_num;
        private int msg_time;
        private String in_time;
        private String out_time;
        private String show_time;
        private String userid;
        private String nick_name;
        private String headpho;
        private String room_id;
        private String sex;
        private String is_follow;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getIcon_img() {
            return icon_img;
        }

        public void setIcon_img(String icon_img) {
            this.icon_img = icon_img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGift_img() {
            return gift_img;
        }

        public void setGift_img(String gift_img) {
            this.gift_img = gift_img;
        }

        public String getGift_name() {
            return gift_name;
        }

        public void setGift_name(String gift_name) {
            this.gift_name = gift_name;
        }

        public String getGift_num() {
            return gift_num;
        }

        public void setGift_num(String gift_num) {
            this.gift_num = gift_num;
        }

        public int getMsg_time() {
            return msg_time;
        }

        public void setMsg_time(int msg_time) {
            this.msg_time = msg_time;
        }

        public String getShow_time() {
            return show_time;
        }

        public void setShow_time(String show_time) {
            this.show_time = show_time;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getHeadpho() {
            return headpho;
        }

        public void setHeadpho(String headpho) {
            this.headpho = headpho;
        }

        public String getRoom_id() {
            return room_id;
        }

        public void setRoom_id(String room_id) {
            this.room_id = room_id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }

        public String getIn_time() {
            return in_time;
        }

        public void setIn_time(String in_time) {
            this.in_time = in_time;
        }

        public String getOut_time() {
            return out_time;
        }

        public void setOut_time(String out_time) {
            this.out_time = out_time;
        }
    }
}
