package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dto.HallDTO;
import dto.PriceListDTO;
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
			Priceslist priceList = priceListService.findByTypeOfExamination(typeOfExamination);
		
			if(priceList == null)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			Clinic c = clinicService.findByName(clinicName);
			
			if(c == null)
			{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); 

			}
			
			for(Appointment app : c.getAppointments())
			{
				if(app.getAppointmentDescription() == priceList.getTypeOfExamination())
				{
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);	
				}
			}
			
			priceList.setDeleted(true);
			priceListService.save(priceList);
			return new ResponseEntity<>(HttpStatus.OK);

		}
	
	 	@GetMapping(value="/getTypeOfExamination/{typeOfExamination}")
	    public ResponseEntity<PriceListDTO> getTypeOfExamination(@PathVariable("typeOfExamination") String typeOfExamination)
	    {
	    	Priceslist priceList= priceListService.findByTypeOfExamination(typeOfExamination);
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
	 	
	 	@PostMapping(value="/update/{typeOfExamination}", consumes = "application/json")
	 	public ResponseEntity<Void> update(@RequestBody PriceListDTO pricesList,@PathVariable("typeOfExamination") String typeOfExamination)
	 	{
	 		Priceslist oldPricesList = priceListService.findByTypeOfExamination(typeOfExamination);
	 		
	 		if(oldPricesList == null)
		    {
			    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
	 		
	 		oldPricesList.setPrice(pricesList.getPrice());
	 		oldPricesList.setTypeOfExamination(pricesList.getTypeOfExamination());
	 		
	 		priceListService.save(oldPricesList);
	    	return new ResponseEntity<>(HttpStatus.OK);
	 	}
	 	@PostMapping(value ="/add", consumes = "application/json")
	    public ResponseEntity<Void> add(@RequestBody PriceListDTO pricesList)
	    {
	       Clinic c = clinicService.findByName(pricesList.getClinicName());
	       
	       if(c == null)
	       {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	       }
	       
	       if(pricesList == null)
	       {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	       }
	        
	       Priceslist pr = new Priceslist(c,pricesList.getTypeOfExamination(),pricesList.getPrice());
	       priceListService.save(pr);
	       return new ResponseEntity<>(HttpStatus.OK);
	    }
	 
}