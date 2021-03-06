package controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.HallDTO;
import dto.PriceListDTO;
import helpers.ListUtil;
import model.Appointment;
import model.Clinic;
import model.Hall;
import model.Priceslist;
import service.AppointmentService;
import service.ClinicService;
import service.PriceListService;

@RestController
@RequestMapping(value = "api/priceList")
public class PriceListControler {

	@Autowired
	private PriceListService priceListService;
	
	@Autowired 
	private ClinicService clinicService;
	
	@Autowired 
	private AppointmentService appointmentService;
	
		@DeleteMapping(value="/deletePriceList/{typeOfExamination}/{clinicName}")
		public ResponseEntity<Void> deletePriceList(@PathVariable ("typeOfExamination") String typeOfExamination,@PathVariable("clinicName") String clinicName)
		{
			Priceslist priceList = priceListService.findByTypeOfExaminationAndClinic(typeOfExamination, clinicName);
		
			if(priceList == null)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			Clinic c = clinicService.findByName(clinicName);
			
			if(c == null)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); 

			}
			
			List<Appointment> appointments = appointmentService.findAllByClinic(c);

			for(Appointment app : appointments)
			{
				if(app.getPriceslist().getTypeOfExamination().equals(priceList.getTypeOfExamination()))
				{
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
				}
			}
			
			priceList.setDeleted(true);
			priceListService.save(priceList);
			return new ResponseEntity<>(HttpStatus.OK);

		}
		@GetMapping(value="/getAllByClinic/{clinicName}")
		public ResponseEntity<List<PriceListDTO>> getAllTypeExaminationByClinic(@PathVariable("clinicName") String clinicName)
		{
			Clinic c  = clinicService.findByName(clinicName);
			
			if(c == null)
			{
				   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			List<Priceslist> pricesList = priceListService.findAllByClinic(c);
			List<PriceListDTO> priceListDTO = new ArrayList<PriceListDTO>();
			
			if(pricesList == null)
			{
				   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			for(Priceslist pr : pricesList)
			{
				if(!pr.getDeleted())
				{
					PriceListDTO dto = new PriceListDTO(pr);
					priceListDTO.add(dto);
				}
			}
			
			return new ResponseEntity<>(priceListDTO,HttpStatus.OK);
		}
		
		@GetMapping(value="/getAll")
		public ResponseEntity<List<PriceListDTO>> getAllTypeExamination()
		{
			List<Priceslist> pricesList = priceListService.findAll();
			List<PriceListDTO> priceListDTO = new ArrayList<PriceListDTO>();
			
			if(pricesList == null)
			{
				   return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			for(Priceslist pr : pricesList)
			{
				if(!pr.getDeleted())
				{
					PriceListDTO dto = new PriceListDTO(pr);
					priceListDTO.add(dto);
				}
			}
			
			return new ResponseEntity<>(priceListDTO,HttpStatus.OK);
		}
	
	 	@GetMapping(value="/get/{typeOfExamination}/{clinicName}")
	    public ResponseEntity<PriceListDTO> getTypeOfExamination(@PathVariable("typeOfExamination") String typeOfExamination, @PathVariable("clinicName") String clinicName)
	    {
	    	Priceslist priceList= priceListService.findByTypeOfExaminationAndClinic(typeOfExamination, clinicName);
	    	if(priceList == null)
	    	{
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	}
	    	
	    	if(priceList.getDeleted())
	    	{
	    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    	}
	    	
	    	PriceListDTO pr = new PriceListDTO(priceList); 
	    	return new ResponseEntity<>(pr,HttpStatus.OK);
	    }
	 	
	 	@PutMapping(value="/update/{typeOfExamination}/{clinicName}")
	 	public ResponseEntity<Void> update(@RequestBody PriceListDTO pricesList,@PathVariable("typeOfExamination") String typeOfExamination, @PathVariable("clinicName") String clinicName)
	 	{
	 		Priceslist oldPricesList = priceListService.findByTypeOfExaminationAndClinic(typeOfExamination, clinicName);
	 		
	 		if(oldPricesList == null)
	 		{
	 			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	 		}
	 		
	 		List<Appointment> apps = appointmentService.findAllByPricesList(oldPricesList);
			
			if(apps != null)
			{
				if(apps.size() > 0)
				{
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				}
			}
	 		
	 		oldPricesList.setPrice(pricesList.getPrice());
	 		oldPricesList.setTypeOfExamination(pricesList.getTypeOfExamination());
	 		
	 		priceListService.save(oldPricesList);
	    	return new ResponseEntity<>(HttpStatus.OK);
	 	}
	 	@PostMapping(value ="/add", consumes = "application/json")
	    public ResponseEntity<Void> add(@RequestBody PriceListDTO dto)
	    {
	 		
	       Clinic c = clinicService.findByName(dto.getClinicName());
	       Priceslist pl = priceListService.findByTypeOfExaminationAndClinic(dto.getTypeOfExamination(), dto.getClinicName());
	       
	       if(c == null)
	       {
	        	return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
	       }
	       
	       if(pl == null)
	       {
	    	   Priceslist newPl = new Priceslist(c,dto.getTypeOfExamination(),dto.getPrice());
	    	   priceListService.save(newPl);
	       }
	       else
	       {
	    	   if(pl.getDeleted() == false) 
	    	   {
	    		   return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);    
	    	   }
	    	   else
	    	   {
	    		   //pl.setDeleted(false)
	    		   //priceListService.save(pl);
	    		   Priceslist newPl = new Priceslist(c,dto.getTypeOfExamination(),dto.getPrice());
		    	   priceListService.save(newPl);
	    	   }

	       }
       
	       return new ResponseEntity<>(HttpStatus.OK);
	    }
	 
}
