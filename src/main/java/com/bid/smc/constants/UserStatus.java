package com.bid.smc.constants;

public enum UserStatus {

		ACTIVE (1),
		INACTIVE (2),
		ALL (3);
		  
	   private UserStatus(int value){
	        this.userStatus = value;
	    }
	    
	   private final int userStatus;

	   public int getVal(){
	        return userStatus;
	    }
	   
	   public static UserStatus getUserStatus(int userStatus){
		   
		   if (userStatus == 1){
			   return ACTIVE;
		   }else if (userStatus == 2){
			   return INACTIVE;
		   }else if (userStatus == 3){
			   return ALL;
		   }
		   return null;	    
	   }
}
