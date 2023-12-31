package io.redeasy.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import fj.data.Collectors;

public class MainClass {

	public static void main(String[] args) {

		Customer customer1 = new Customer(100L, "Juan", "Gonzales", 8);
		Customer customer2 = new Customer(200L, "Pedro", "Gutierres", 5);

		Predicate<Customer> predicate = x -> x.creditRating >= 6;

		System.out.println("Credit approved test customer1: " + predicate.test(customer1));
		System.out.println("Credit approved test customer2: " + predicate.test(customer2));

		Consumer<Customer> consumer = x -> System.out.println(x.toString());
		consumer.accept(customer2);

		Supplier<Customer> supplier = () -> new Customer(300L, "Pablo", "Cespedes", 7);
		System.out.println("customer3:" + supplier.get().toString());

		Customer customer4 = new Customer(400L, "Julio", "Requena", 4, 9);
		CustomerRatingInterface<Double, Double, Double> customerRating = (x, y) -> x * 0.8 + y * 0.5;
		System.out.println("customer4 rating:"
				+ customerRating.getRating(customer4.getCreditRating(), customer4.getSalaryRating()));

		System.out.println("customer4 rating approved:"
				+ customerRating.isApproved(customer4.getCreditRating(), customer4.getSalaryRating()));

		MethodReference reference = new MethodReference();
		CustomerRatingInterface<Double, Double, Double> customerRatingReference = reference::rating;
		System.out.println("customerX rating approved:" + customerRatingReference.isApproved(4, 5));

		customer4.setAlias(null);
		Optional<String> alternativeAlias = customer4.getAliasWithOptional();
		if (alternativeAlias.isPresent()) {
			System.out.println("alias size:" + alternativeAlias.get().length());
		} else {
			System.out.println(alternativeAlias.orElse("paco").length());
			//System.out.println(alternativeAlias.orElseGet(Customer::getName).length());
		}

		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer1);
		customers.add(customer2);
		customers.add(customer4);
		
		customers.forEach(x -> System.out.println(x.getName()));
		
		customers.removeIf(x -> x.getName().equals("Pedro"));
		
		customers.forEach(x -> System.out.println(x.getName()));
		
		Stream<String> brandsStream = Stream.of("BMW", "Audi","Mercedes Bens", "Volvo","Lexus");
		brandsStream.forEach(System.out::println);
							
		List<String> brands = new ArrayList<String>();
		brands.add("BMW");
		brands.add("Audi");
		brands.add("Volvo");
		Stream<String> brandsTopStream = brands.stream();
		brandsTopStream.parallel().forEach(System.out::println);
		
		//List<String> brandsList = (List<String>) brandsTopStream.collect(Collectors.toList());
		
		Stream<String> stringCredits =  Stream.of("100","200","300","400","500","600","700","800");
		Stream<Double> creditsIvaStream = stringCredits.map(x->Double.parseDouble(x)*10*0.18);
		
		creditsIvaStream.filter(x -> x >= 800.0)
		                .forEach(System.out::println);
		
		
		
		
	}
}
