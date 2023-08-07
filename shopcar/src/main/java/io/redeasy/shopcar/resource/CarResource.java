package io.redeasy.shopcar.resource;

import java.net.URI;
import java.util.List;

import io.redeasy.shopcar.entity.Car;
import io.redeasy.shopcar.entity.Shop;
import io.redeasy.shopcar.repository.CarRepository;
import io.redeasy.shopcar.repository.ShopRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cars")
public class CarResource {

   @Inject
   CarRepository carRepository;
   
   @Inject
   ShopRepository shopRepository;
   
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response getAll() {
	   List<Car> cars = carRepository.listAll();
	   return Response.ok(cars).build();
   }
   
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @Path("{id}")
   public Response getById(@PathParam("id") Long id) {
	   Car car = carRepository.findById(id);
	   if(carRepository.isPersistent(car)) {
		   return Response.ok(car).build();
	   }
	   return Response.status(Response.Status.NOT_FOUND).build();
   }
   
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @Path("brand/{brand}")
   public Response getByBrand(@PathParam("brand") String brand) {
	   List<Car> cars = carRepository.list("SELECT c FROM Car  c WHERE c.brand = ?1 ORDER BY id DESC", brand);
	   return Response.ok(cars).build();
   }
   
   @POST
   @Transactional
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response create(Car car) {
	   carRepository.persist(car);
	   if(carRepository.isPersistent(car)) {
		   return Response.created(URI.create("cars" +  car.id)).build();
	   }
	   return Response.status(Response.Status.BAD_REQUEST).build();
   }
   
   @DELETE
   @Transactional
   @Path("{id}")
   public Response deleteById(@PathParam("id") Long id) {
	   boolean deleted = carRepository.deleteById(id);
	   if(deleted) {
		   return Response.noContent().build();
	   }
	   return Response.status(Response.Status.BAD_REQUEST).build();
   }
   
   //TODO: Crear el metodo para asignar una tienda al car  
   @PUT
   @Transactional
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Path("{id}/shop/{idshop}")
   public Response addToShop(@PathParam("id") Long id,@PathParam("idshop") Long idShop) {
   	Car car = carRepository.findById(id);
   	Shop shop = shopRepository.findById(idShop);
		if (car != null && shop!=null) {
			car.setShop(shop);
			carRepository.persist(car);
			return Response.ok(car).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
   }
   
   
   @GET
   @Produces(MediaType.TEXT_PLAIN)
   @Path("size")
   public String getSize() {
	   return String.format("{\"size\": %d}", carRepository.count());
   }
   
}
