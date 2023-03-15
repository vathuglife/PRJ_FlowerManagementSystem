/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author VietAnhOdyssey
 */
public class Category {
    private int CateId;
    private String catename;

    public Category(int CateId, String catename) {
        this.CateId = CateId;
        this.catename = catename;
    }

    public int getCateId() {
        return CateId;
    }

    public void setCateId(int CateId) {
        this.CateId = CateId;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }
    
    
}
