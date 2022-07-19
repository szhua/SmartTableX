/**
  * Copyright 2022 json.cn 
  */
package com.fencer.lock;
import java.util.List;

/**
 * Auto-generated: 2022-06-13 16:33:59
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class RiverCenterRootBean {

    private List<RiverCenterSubBean> list;
    private String message;
    private String status;


    public static class  RiverCenterSubBean{
        String gxsj ;
        List<CenterBean> list ;
        String sqtjid ;

        public String getGxsj() {
            return gxsj;
        }

        public void setGxsj(String gxsj) {
            this.gxsj = gxsj;
        }

        public List<CenterBean> getList() {
            return list;
        }

        public void setList(List<CenterBean> list) {
            this.list = list;
        }

        public String getSqtjid() {
            return sqtjid;
        }

        public void setSqtjid(String sqtjid) {
            this.sqtjid = sqtjid;
        }
    }

    public List<RiverCenterSubBean> getList() {
        return list;
    }

    public void setList(List<RiverCenterSubBean> list) {
        this.list = list;
    }

    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

}