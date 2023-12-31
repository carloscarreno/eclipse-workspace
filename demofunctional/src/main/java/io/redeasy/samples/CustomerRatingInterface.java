package io.redeasy.samples;

@FunctionalInterface
public interface CustomerRatingInterface<T,U,P> {
   
	public Double getRating(int i,int j);
   
	public default boolean isApproved(int i,int j) {
		if(getRating(i, j)>=6.0) {
			return true;
		}
		return false;
	}
	
}
