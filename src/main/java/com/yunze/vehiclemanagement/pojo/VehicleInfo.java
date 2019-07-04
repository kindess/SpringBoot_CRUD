package com.yunze.vehiclemanagement.pojo;

import java.io.Serializable;

public class VehicleInfo implements Serializable {
    private String id;

    private String vehicleLicense;

    private String owener;

    private Integer optionColorId;

    private String engineNumber;

    private Integer vehicleBrandId;

    private String vehicleRackNumber;

    private String vehicleModel;

    private String registerDate;

    private Integer vehicleTypeId;

    private String dateInitialRegistration;

    private String bodyColor;

    private Integer vehicleMaintenanceStatusId;

    private Integer seatingCapacity;

    private Integer annualReviewStatusId;

    private Integer fuelTypeId;

    private String nextAnnualInspectionTime;

    private Integer displacement;

    private Integer levelId;

    private Integer businessPurposeId;

    private Integer scopeOfBusinessId;

    private Integer subordinateSuppliersId;

    private Integer seatsOfGuests;

    private Integer mileage;

    private String drivingLicenseNumber;

    private Integer dispatchingRightId;

    private String remarks;

    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getVehicleLicense() {
        return vehicleLicense;
    }

    public void setVehicleLicense(String vehicleLicense) {
        this.vehicleLicense = vehicleLicense == null ? null : vehicleLicense.trim();
    }

    public String getOwener() {
        return owener;
    }

    public void setOwener(String owener) {
        this.owener = owener == null ? null : owener.trim();
    }

    public Integer getOptionColorId() {
        return optionColorId;
    }

    public void setOptionColorId(Integer optionColorId) {
        this.optionColorId = optionColorId;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber == null ? null : engineNumber.trim();
    }

    public Integer getVehicleBrandId() {
        return vehicleBrandId;
    }

    public void setVehicleBrandId(Integer vehicleBrandId) {
        this.vehicleBrandId = vehicleBrandId;
    }

    public String getVehicleRackNumber() {
        return vehicleRackNumber;
    }

    public void setVehicleRackNumber(String vehicleRackNumber) {
        this.vehicleRackNumber = vehicleRackNumber == null ? null : vehicleRackNumber.trim();
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel == null ? null : vehicleModel.trim();
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate == null ? null : registerDate.trim();
    }

    public Integer getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(Integer vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }

    public String getDateInitialRegistration() {
        return dateInitialRegistration;
    }

    public void setDateInitialRegistration(String dateInitialRegistration) {
        this.dateInitialRegistration = dateInitialRegistration == null ? null : dateInitialRegistration.trim();
    }

    public String getBodyColor() {
        return bodyColor;
    }

    public void setBodyColor(String bodyColor) {
        this.bodyColor = bodyColor == null ? null : bodyColor.trim();
    }

    public Integer getVehicleMaintenanceStatusId() {
        return vehicleMaintenanceStatusId;
    }

    public void setVehicleMaintenanceStatusId(Integer vehicleMaintenanceStatusId) {
        this.vehicleMaintenanceStatusId = vehicleMaintenanceStatusId;
    }

    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public Integer getAnnualReviewStatusId() {
        return annualReviewStatusId;
    }

    public void setAnnualReviewStatusId(Integer annualReviewStatusId) {
        this.annualReviewStatusId = annualReviewStatusId;
    }

    public Integer getFuelTypeId() {
        return fuelTypeId;
    }

    public void setFuelTypeId(Integer fuelTypeId) {
        this.fuelTypeId = fuelTypeId;
    }

    public String getNextAnnualInspectionTime() {
        return nextAnnualInspectionTime;
    }

    public void setNextAnnualInspectionTime(String nextAnnualInspectionTime) {
        this.nextAnnualInspectionTime = nextAnnualInspectionTime == null ? null : nextAnnualInspectionTime.trim();
    }

    public Integer getDisplacement() {
        return displacement;
    }

    public void setDisplacement(Integer displacement) {
        this.displacement = displacement;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getBusinessPurposeId() {
        return businessPurposeId;
    }

    public void setBusinessPurposeId(Integer businessPurposeId) {
        this.businessPurposeId = businessPurposeId;
    }

    public Integer getScopeOfBusinessId() {
        return scopeOfBusinessId;
    }

    public void setScopeOfBusinessId(Integer scopeOfBusinessId) {
        this.scopeOfBusinessId = scopeOfBusinessId;
    }

    public Integer getSubordinateSuppliersId() {
        return subordinateSuppliersId;
    }

    public void setSubordinateSuppliersId(Integer subordinateSuppliersId) {
        this.subordinateSuppliersId = subordinateSuppliersId;
    }

    public Integer getSeatsOfGuests() {
        return seatsOfGuests;
    }

    public void setSeatsOfGuests(Integer seatsOfGuests) {
        this.seatsOfGuests = seatsOfGuests;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber == null ? null : drivingLicenseNumber.trim();
    }

    public Integer getDispatchingRightId() {
        return dispatchingRightId;
    }

    public void setDispatchingRightId(Integer dispatchingRightId) {
        this.dispatchingRightId = dispatchingRightId;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }
}