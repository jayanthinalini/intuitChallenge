package com.intuit.cg.backendtechassessment.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.intuit.cg.backendtechassessment.entity.common.Auditable;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Bid extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    @ManyToOne
    private Project project;

    @NotNull
    @Min(value = 0)
    private BigDecimal amount;
    
    // variables used for autoBid feature

    @NotNull
    private Boolean autoBid = false;
    
    @NotNull
    @Min(value = 0)
    private BigDecimal decrement = BigDecimal.ZERO;
    
    @NotNull
    @Min(value = 0)
    private BigDecimal minAmount = BigDecimal.ZERO;
    
    
	public Boolean getAutoBid() {
		return autoBid;
	}

	public void setAutoBid(Boolean autoBid) {
		this.autoBid = autoBid;
	}

	public BigDecimal getDecrement() {
		return decrement;
	}

	public void setDecrement(BigDecimal decrement) {
		this.decrement = decrement;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
    
    
}
