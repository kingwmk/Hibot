package cn.ybz21.hibotvoice.bean;

import java.io.Serializable;

/**
 * Created by smarf on 2016/12/24.
 */

public class PushDataBean implements Serializable{
    public String content;
    public String url;

    public PushDataBean(String content, String url) {
        this.content = content;
        this.url = url;
    }

    public PushDataBean() {
    }
}
