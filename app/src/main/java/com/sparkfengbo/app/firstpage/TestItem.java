package com.sparkfengbo.app.firstpage;

/**
 * Created by fengbo on 2018/2/7.
 */

public class TestItem extends BaseItemData {

    public int catogory;

    public TestItem(String title, int catogory, int id) {
        this.type = TYPE_ITEM;
        this.title = title;
        this.catogory = catogory;
        this.id = id;
    }
}
