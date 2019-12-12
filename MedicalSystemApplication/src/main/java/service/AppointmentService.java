package service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Appointment;
import model.Clinic;
import model.Doctor;
import model.Hall;
import model.Patient;
import model.User;
import repository.AppointmentRepository;
import repository.AppointmentRequestRepository;
import repository.ClinicRepository;
import repository.HallRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired 
	private HallRepository hallRepository;
	
	@Autowired
	private ClinicRepository clinicRepository;
	
	public Appointment findAppointment(Date date, Hall hall, Clinic clinic)
	{
		return appointmentRepository.findByDateAndHallAndClinic(date, hall, clinic);
	}
	
	public List<Appointment> findAll()
	{
		return appointmentRepository.findAll();
	}
	
	public Appointment findAppointment(String date, int hallNumber, String clinic)
	{
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		try {
			Date d = df.parse(date);
			
			Hall h = hallRepository.findByNumber(hallNumber);
			Clinic c = clinicRepository.findByName(clinic);
			
			return findAppointment(d,h,c);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void save(Appointment appointment)
	{
		appointmentRepository.save(appointment);
	}
	
	public List<Appointment> findAllByPatient(Patient p)
	{
		return appointmentRepository.findAllByPatient(p);
	}
	
	public List<Appointment> findAllByDoctor(Long doctorID)
	{
		return appointmentRepository.findAllByDoctor(doctorID);
	}
	
	public List<Appointment> findAllByDoctor(Doctor d)
	{
		List<Appointment> apps = appointmentRepository.findAll();
		List<Appointment> ret = new ArrayList<Appointment>();
		for(Appointment app : apps)
		{
			List<Doctor> doctors = app.getDoctors();
			
			for(Doctor doc : doctors)
			{
				if(doc.getEmail().equals(d.getEmail()))
				{
					ret.add(app);
				}
			}
		}
		
		return ret;
	}
	
	public List<Appointment> findAllByHall(Hall hall)
	{
		return appointmentRepository.findAllByHall(hall);
	}
	
	public List<Appointment> findAllByClinic(Clinic c)
	{
		return appointmentRepository.findAllByClinic(c);
	}
}
