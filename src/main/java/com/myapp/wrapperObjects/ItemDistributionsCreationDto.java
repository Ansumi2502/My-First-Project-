package com.myapp.wrapperObjects;

import java.util.List;


import com.myapp.modal.ItemDistributionDetails;

public class ItemDistributionsCreationDto {
	
	private List<ItemDistributionDetails> itemDistributionDetails;
	
	

	public List<ItemDistributionDetails> getItemDistributionDetails() {
		return itemDistributionDetails;
	}

	public void setItemDistributionDetails(List<ItemDistributionDetails> itemDistributionDetails) {
		this.itemDistributionDetails = itemDistributionDetails;
	}

    public void addItemDistributionDetails(ItemDistributionDetails itemDistributionDetails) {
        this.itemDistributionDetails.add(itemDistributionDetails);
    }
	
}
