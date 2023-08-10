package io.redeasy.shopcar.resource;

import java.net.URI;
import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.redeasy.shopcar.entity.Car;
import io.redeasy.shopcar.entity.Shop;
import io.redeasy.shopcar.proxy.CarProxy;
import io.redeasy.shopcar.repository.ShopRepository;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/shops")
public class ShopResource {
   
   @RestClient
   CarProxy carProxy;
   
   @Inject
   ShopRepository shopRepository;
   
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public Response getAll() {
	   List<Shop> shops = shopRepository.listAll();
	   return Response.ok(shops).build();
   }
   
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @Path("{id}")
   public Response getById(@PathParam("id") Long id) {
	   Shop shop = shopRepository.findById(id);
	   if(shopRepository.isPersistent(shop)) {
		   return Response.ok(shop).build();
	   }
	   return Response.status(Response.Status.NOT_FOUND).build();
   }
   
   @POST
   @Transactional
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   public Response create(Shop shop) {
	   shopRepository.persist(shop);
	   if(shopRepository.isPersistent(shop)) {
		   return Response.created(URI.create("Shops" +  shop.id)).build();
	   }
	   return Response.status(Response.Status.BAD_REQUEST).build();
   }
   
   @DELETE
   @Transactional
   @Path("{id}")
   public Response deleteById(@PathParam("id") Long id) {
	   boolean deleted = shopRepository.deleteById(id);
	   if(deleted) {
		   return Response.noContent().build();
	   }
	   return Response.status(Response.Status.BAD_REQUEST).build();
   }
   
   @GET
   @Path("{id}/cars/reactive")
   @Produces(MediaType.APPLICATION_JSON)
   public Uni<Response> getCarsReactive(@PathParam("id") Long id) {
	   System.out.println("start process");
	   Uni<Response> responseCars = carProxy.getCarsByShopIdReactive(id).onItem().transform(x -> {
		   System.out.println("data received");
		   return Response.ok(x).build();
	   });
	   System.out.println("end process");
	   return  responseCars;
   }
   
   @GET
   @Path("{id}/cars")
   //@Blocking
   @Produces(MediaType.APPLICATION_JSON)
   public Response getCars(@PathParam("id") Long id) {
	   System.out.println("start process");
	   List<Car> cars = carProxy.getCarsByShopId(id);
	   System.out.println("data received");
	   System.out.println("end process");
	   return  Response.ok(cars).build();
   }
   
}
