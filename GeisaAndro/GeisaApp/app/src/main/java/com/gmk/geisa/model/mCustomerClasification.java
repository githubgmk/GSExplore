package com.gmk.geisa.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kenjin on 12/01/18.
 */

public class mCustomerClasification implements Serializable {
    private int RecId;
    private int CustId;
    private int ChannelWayId;
    private int ChannelGradeId;
    private int ChannelAppId;
    private int ChannelStagingId;
    private String PeriodeStart;
    private String PeriodeEnd;
    private int Active;
    private String CreatedDate;

    private String ChannelCode;
    private String ChannelWayName;
    private int ChannelGroupId;
    private String Description;
    private int Status;

    private int ChannelId;
    private String ChannelName;
    private String Email;
    private String Signature;
    private String Pic;
    private int StatusId;

    private String ChannelGradeCode;
    private String ChannelGradeName;
    private String ChannelGradeDescription;

    private String ChannelAppCode;
    private String ChannelAppName;

    private String ChannelStagingCode;
    private String ChannelStagingName;
    private String ChannelStagingDescription;
    private String ChannelStagingShareWalet;

    public mCustomerClasification() {

    }

    public mCustomerClasification(int recId, int custId, int channelWayId, int channelGradeId, int channelAppId, int channelStagingId, String periodeStart, String periodeEnd, int active, String channelCode, String channelWayName, int channelGroupId, String description, int status, int channelId, String channelName, String email, String signature, String pic, int statusId, String channelGradeCode, String channelGradeName, String channelGradeDescription, String channelAppCode, String channelAppName, String channelStagingCode, String channelStagingName, String channelStagingDescription, String channelStagingShareWalet, ArrayList<ChannelStagingApproach> stagingApproach, ArrayList<ChannelProduk> produk) {
        RecId = recId;
        CustId = custId;
        ChannelWayId = channelWayId;
        ChannelGradeId = channelGradeId;
        ChannelAppId = channelAppId;
        ChannelStagingId = channelStagingId;
        PeriodeStart = periodeStart;
        PeriodeEnd = periodeEnd;
        Active = active;
        ChannelCode = channelCode;
        ChannelWayName = channelWayName;
        ChannelGroupId = channelGroupId;
        Description = description;
        Status = status;
        ChannelId = channelId;
        ChannelName = channelName;
        Email = email;
        Signature = signature;
        Pic = pic;
        StatusId = statusId;
        ChannelGradeCode = channelGradeCode;
        ChannelGradeName = channelGradeName;
        ChannelGradeDescription = channelGradeDescription;
        ChannelAppCode = channelAppCode;
        ChannelAppName = channelAppName;
        ChannelStagingCode = channelStagingCode;
        ChannelStagingName = channelStagingName;
        ChannelStagingDescription = channelStagingDescription;
        ChannelStagingShareWalet = channelStagingShareWalet;
        StagingApproach = stagingApproach;
        Produk = produk;
    }

    public mCustomerClasification(int recId, int custId, int channelWayId, int channelGradeId, int channelAppId, int channelStagingId, String periodeStart, String periodeEnd, int active, String channelCode, String channelWayName, int channelGroupId, String description, int status, int channelId, String channelName, String email, String signature, String pic, int statusId, String channelGradeCode, String channelGradeName, String channelGradeDescription, String channelAppCode, String channelAppName, String channelStagingCode, String channelStagingName, String channelStagingDescription, String channelStagingShareWalet) {
        RecId = recId;
        CustId = custId;
        ChannelWayId = channelWayId;
        ChannelGradeId = channelGradeId;
        ChannelAppId = channelAppId;
        ChannelStagingId = channelStagingId;
        PeriodeStart = periodeStart;
        PeriodeEnd = periodeEnd;
        Active = active;
        ChannelCode = channelCode;
        ChannelWayName = channelWayName;
        ChannelGroupId = channelGroupId;
        Description = description;
        Status = status;
        ChannelId = channelId;
        ChannelName = channelName;
        Email = email;
        Signature = signature;
        Pic = pic;
        StatusId = statusId;
        ChannelGradeCode = channelGradeCode;
        ChannelGradeName = channelGradeName;
        ChannelGradeDescription = channelGradeDescription;
        ChannelAppCode = channelAppCode;
        ChannelAppName = channelAppName;
        ChannelStagingCode = channelStagingCode;
        ChannelStagingName = channelStagingName;
        ChannelStagingDescription = channelStagingDescription;
        ChannelStagingShareWalet = channelStagingShareWalet;
    }

    private ArrayList<ChannelStagingApproach> StagingApproach;
    private ArrayList<ChannelProduk> Produk;

    public int getRecId() {
        return RecId;
    }

    public void setRecId(int recId) {
        RecId = recId;
    }

    public int getCustId() {
        return CustId;
    }

    public void setCustId(int custId) {
        CustId = custId;
    }

    public int getChannelWayId() {
        return ChannelWayId;
    }

    public void setChannelWayId(int channelWayId) {
        ChannelWayId = channelWayId;
    }

    public int getChannelGradeId() {
        return ChannelGradeId;
    }

    public void setChannelGradeId(int channelGradeId) {
        ChannelGradeId = channelGradeId;
    }

    public int getChannelAppId() {
        return ChannelAppId;
    }

    public void setChannelAppId(int channelAppId) {
        ChannelAppId = channelAppId;
    }

    public int getChannelStagingId() {
        return ChannelStagingId;
    }

    public void setChannelStagingId(int channelStagingId) {
        ChannelStagingId = channelStagingId;
    }

    public String getPeriodeStart() {
        return PeriodeStart;
    }

    public void setPeriodeStart(String periodeStart) {
        PeriodeStart = periodeStart;
    }

    public String getPeriodeEnd() {
        return PeriodeEnd;
    }

    public void setPeriodeEnd(String periodeEnd) {
        PeriodeEnd = periodeEnd;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public int getActive() {
        return Active;
    }

    public void setActive(int active) {
        Active = active;
    }

    public String getChannelCode() {
        return ChannelCode;
    }

    public void setChannelCode(String channelCode) {
        ChannelCode = channelCode;
    }

    public String getChannelWayName() {
        return ChannelWayName;
    }

    public void setChannelWayName(String channelWayName) {
        ChannelWayName = channelWayName;
    }

    public int getChannelGroupId() {
        return ChannelGroupId;
    }

    public void setChannelGroupId(int channelGroupId) {
        ChannelGroupId = channelGroupId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getChannelId() {
        return ChannelId;
    }

    public void setChannelId(int channelId) {
        ChannelId = channelId;
    }

    public String getChannelName() {
        return ChannelName;
    }

    public void setChannelName(String channelName) {
        ChannelName = channelName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public String getPic() {
        return Pic;
    }

    public void setPic(String pic) {
        Pic = pic;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    public String getChannelGradeCode() {
        return ChannelGradeCode;
    }

    public void setChannelGradeCode(String channelGradeCode) {
        ChannelGradeCode = channelGradeCode;
    }

    public String getChannelGradeName() {
        return ChannelGradeName;
    }

    public void setChannelGradeName(String channelGradeName) {
        ChannelGradeName = channelGradeName;
    }

    public String getChannelGradeDescription() {
        return ChannelGradeDescription;
    }

    public void setChannelGradeDescription(String channelGradeDescription) {
        ChannelGradeDescription = channelGradeDescription;
    }

    public String getChannelAppCode() {
        return ChannelAppCode;
    }

    public void setChannelAppCode(String channelAppCode) {
        ChannelAppCode = channelAppCode;
    }

    public String getChannelAppName() {
        return ChannelAppName;
    }

    public void setChannelAppName(String channelAppName) {
        ChannelAppName = channelAppName;
    }

    public String getChannelStagingCode() {
        return ChannelStagingCode;
    }

    public void setChannelStagingCode(String channelStagingCode) {
        ChannelStagingCode = channelStagingCode;
    }

    public String getChannelStagingName() {
        return ChannelStagingName;
    }

    public void setChannelStagingName(String channelStagingName) {
        ChannelStagingName = channelStagingName;
    }

    public String getChannelStagingDescription() {
        return ChannelStagingDescription;
    }

    public void setChannelStagingDescription(String channelStagingDescription) {
        ChannelStagingDescription = channelStagingDescription;
    }

    public String getChannelStagingShareWalet() {
        return ChannelStagingShareWalet;
    }

    public void setChannelStagingShareWalet(String channelStagingShareWalet) {
        ChannelStagingShareWalet = channelStagingShareWalet;
    }

    public ArrayList<ChannelStagingApproach> getStagingApproach() {
        return StagingApproach;
    }

    public void setStagingApproach(ArrayList<ChannelStagingApproach> stagingApproach) {
        StagingApproach = stagingApproach;
    }

    public ArrayList<ChannelProduk> getProduk() {
        return Produk;
    }

    public void setProduk(ArrayList<ChannelProduk> produk) {
        Produk = produk;
    }

    public class ChannelProduk implements Serializable {
        private int ChannelAppProdukRelasiId;
        private int ChannelAppId;
        private int ProdukId;
        private int Status;
        private String ProductName;
        private String ProductSimpleDescription;

        public ChannelProduk(int channelAppProdukRelasiId, int channelAppId, int produkId, int status, String productName, String productSimpleDescription) {
            ChannelAppProdukRelasiId = channelAppProdukRelasiId;
            ChannelAppId = channelAppId;
            ProdukId = produkId;
            Status = status;
            ProductName = productName;
            ProductSimpleDescription = productSimpleDescription;
        }

        public int getChannelAppProdukRelasiId() {
            return ChannelAppProdukRelasiId;
        }

        public void setChannelAppProdukRelasiId(int channelAppProdukRelasiId) {
            ChannelAppProdukRelasiId = channelAppProdukRelasiId;
        }

        public int getChannelAppId() {
            return ChannelAppId;
        }

        public void setChannelAppId(int channelAppId) {
            ChannelAppId = channelAppId;
        }

        public int getProdukId() {
            return ProdukId;
        }

        public void setProdukId(int produkId) {
            ProdukId = produkId;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }

        public String getProductSimpleDescription() {
            return ProductSimpleDescription;
        }

        public void setProductSimpleDescription(String productSimpleDescription) {
            ProductSimpleDescription = productSimpleDescription;
        }
    }

    public class ChannelStagingApproach implements Serializable {
        private int RecId;
        private int ChannelStagingItemId;
        private int ChannelStagingId;
        private String ChannelStagingFor;
        private int ChannelStagingApproachStatus;

        private String ChannelStagingCode;
        private String ChannelStagingName;
        private String ChannelStagingDescription;
        private String ChannelStagingShareWalet;
        private ArrayList<Item> Item;
        //tambahan type
        private int mType;

        public int getType() {
            return mType;
        }

        public void setType(int mType) {
            this.mType = mType;
        }

        public ChannelStagingApproach(int recId, int channelStagingItemId, int channelStagingId, String channelStagingFor, int channelStagingApproachStatus, String channelStagingCode, String channelStagingName, String channelStagingDescription, String channelStagingShareWalet) {
            RecId = recId;
            ChannelStagingItemId = channelStagingItemId;
            ChannelStagingId = channelStagingId;
            ChannelStagingFor = channelStagingFor;
            ChannelStagingApproachStatus = channelStagingApproachStatus;
            ChannelStagingCode = channelStagingCode;
            ChannelStagingName = channelStagingName;
            ChannelStagingDescription = channelStagingDescription;
            ChannelStagingShareWalet = channelStagingShareWalet;
        }

        public int getRecId() {
            return RecId;
        }

        public void setRecId(int recId) {
            RecId = recId;
        }

        public int getChannelStagingItemId() {
            return ChannelStagingItemId;
        }

        public void setChannelStagingItemId(int channelStagingItemId) {
            ChannelStagingItemId = channelStagingItemId;
        }

        public int getChannelStagingId() {
            return ChannelStagingId;
        }

        public void setChannelStagingId(int channelStagingId) {
            ChannelStagingId = channelStagingId;
        }

        public String getChannelStagingFor() {
            return ChannelStagingFor;
        }

        public void setChannelStagingFor(String channelStagingFor) {
            ChannelStagingFor = channelStagingFor;
        }

        public int getChannelStagingApproachStatus() {
            return ChannelStagingApproachStatus;
        }

        public void setChannelStagingApproachStatus(int channelStagingApproachStatus) {
            ChannelStagingApproachStatus = channelStagingApproachStatus;
        }

        public String getChannelStagingCode() {
            return ChannelStagingCode;
        }

        public void setChannelStagingCode(String channelStagingCode) {
            ChannelStagingCode = channelStagingCode;
        }

        public String getChannelStagingName() {
            return ChannelStagingName;
        }

        public void setChannelStagingName(String channelStagingName) {
            ChannelStagingName = channelStagingName;
        }

        public String getChannelStagingDescription() {
            return ChannelStagingDescription;
        }

        public void setChannelStagingDescription(String channelStagingDescription) {
            ChannelStagingDescription = channelStagingDescription;
        }

        public String getChannelStagingShareWalet() {
            return ChannelStagingShareWalet;
        }

        public void setChannelStagingShareWalet(String channelStagingShareWalet) {
            ChannelStagingShareWalet = channelStagingShareWalet;
        }


        public ArrayList<ChannelStagingApproach.Item> getItem() {
            return Item;
        }

        public void setItem(ArrayList<ChannelStagingApproach.Item> item) {
            Item = item;
        }

        public class Item implements Serializable {
            private int ChannelStagingItemId;
            private String ChannelStagingItemCode;
            private String ChannelStagingItemName;
            private String ChannelStagingItemDesc;
            private int ChannelStagingItemStatus;

            public Item() {
            }

            public Item(int channelStagingItemId, String channelStagingItemCode, String channelStagingItemName, String channelStagingItemDesc, int channelStagingItemStatus) {
                ChannelStagingItemId = channelStagingItemId;
                ChannelStagingItemCode = channelStagingItemCode;
                ChannelStagingItemName = channelStagingItemName;
                ChannelStagingItemDesc = channelStagingItemDesc;
                ChannelStagingItemStatus = channelStagingItemStatus;
            }

            public int getChannelStagingItemId() {
                return ChannelStagingItemId;
            }

            public void setChannelStagingItemId(int channelStagingItemId) {
                ChannelStagingItemId = channelStagingItemId;
            }

            public String getChannelStagingItemCode() {
                return ChannelStagingItemCode;
            }

            public void setChannelStagingItemCode(String channelStagingItemCode) {
                ChannelStagingItemCode = channelStagingItemCode;
            }

            public String getChannelStagingItemName() {
                return ChannelStagingItemName;
            }

            public void setChannelStagingItemName(String channelStagingItemName) {
                ChannelStagingItemName = channelStagingItemName;
            }

            public String getChannelStagingItemDesc() {
                return ChannelStagingItemDesc;
            }

            public void setChannelStagingItemDesc(String channelStagingItemDesc) {
                ChannelStagingItemDesc = channelStagingItemDesc;
            }

            public int getChannelStagingItemStatus() {
                return ChannelStagingItemStatus;
            }

            public void setChannelStagingItemStatus(int channelStagingItemStatus) {
                ChannelStagingItemStatus = channelStagingItemStatus;
            }
        }


    }
}
