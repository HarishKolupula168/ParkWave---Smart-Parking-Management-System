package com.parkwave.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "mall_parking_slots")
public class MallParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "mall_id")
    private Mall mall;

    @Column(name = "slot_number")
    private String slotNumber;

    @Column(name = "level")
    private int level;

    @Column(name = "row_position")
    private char rowPosition;

    @Column(name = "column_position")
    private int columnPosition;

    @Column(name = "zone")
    private String zone; // A, B, C, D zones

    @Column(name = "priority")
    private String priority; // NEAR_ENTRANCE, MIDDLE, FAR

    @Column(name = "status")
    private String status; // AVAILABLE, BOOKED, RESERVED

    @Column(name = "slot_type")
    private String slotType; // REGULAR, HANDICAPPED, VIP, ELECTRIC

    public MallParkingSlot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mall getMall() {
        return mall;
    }

    public void setMall(Mall mall) {
        this.mall = mall;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public char getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(char rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }
}
