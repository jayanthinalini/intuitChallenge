package com.intuit.cg.backendtechassessment.entity;

import com.fasterxml.jackson.annotation.*;
import com.intuit.cg.backendtechassessment.entity.common.Auditable;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Project extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Seller seller;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids;

    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

    @Min(value = 0)
    @NotNull
    private BigDecimal maxBidAmount;
    @Future
    private LocalDateTime bidDeadline;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public List<Bid> getBids() {
		return bids;
	}
	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getMaxBidAmount() {
		return maxBidAmount;
	}
	public void setMaxBidAmount(BigDecimal maxBidAmount) {
		this.maxBidAmount = maxBidAmount;
	}
	public LocalDateTime getBidDeadline() {
		return bidDeadline;
	}
	public void setBidDeadline(LocalDateTime bidDeadline) {
		this.bidDeadline = bidDeadline;
	}
    
    
}
