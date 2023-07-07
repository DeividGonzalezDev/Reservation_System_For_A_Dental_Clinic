import { getCookie } from "./utilsFunctions.js";

export async function saveDentist(
  name,
  surname,
  licenseNumber,
  homeAddress = null
) {
    const res = await fetch("http://localhost:8080/dentists/save", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + getCookie("userData").jwt,
      },
      body: JSON.stringify({
        name: name,
        surname: surname,
        licenseNumber: licenseNumber,
        homeAddress: homeAddress,
      }),
    });
    let error = {};
    let resParsed = {};
    if(res.status === 200){
      resParsed = await res.json();
    
    } else{
      error = await res.json();
      throw error;
    }
    console.log(res);
    return resParsed;
  
}

export async function findAllDentists() {
  const res = await fetch("http://localhost:8080/dentists/findAll", {
    method: "GET",
    headers: {
      Authorization: "Bearer " + getCookie("userData").jwt,
    }
  });
  let error = {};
  let resParsed = {};
  if(res.status === 200){
    resParsed = await res.json();
  } else{
    error = await res.json();
    throw error;
  }
  return resParsed;
}

export async function findDentistById(id) {
  const res = await fetch("http://localhost:8080/dentists/findById?id=" + id, {
    method: "GET",
    headers: {
      Authorization: "Bearer " + getCookie("userData").jwt,
    }
  });
  let error = {};
  let resParsed = {};
  if(res.status === 200){
    resParsed = await res.json();
  } else{
    error = await res.json();
    throw error;
  }
  return resParsed;
}

export async function deleteDentistById(id) {
  const res = await fetch("http://localhost:8080/dentists/delete?id=" + id, {
    method: "DELETE",
    headers: {
      Authorization: "Bearer " + getCookie("userData").jwt,
    }
  });
  let error = {};
  let resParsed = {};
  if(res.status === 200){
    resParsed = await res.json();
  } else{
    error = await res.json();
    throw error;
  }
  return resParsed;
}

export async function updateDentistById(id=null, name, surname, licenseNumber, homeAddress=null) {
  const res = await fetch("http://localhost:8080/dentists/update", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + getCookie("userData").jwt,
    },
    body: JSON.stringify({
      id: id,
      name: name,
      surname: surname,
      licenseNumber: licenseNumber,
      homeAddress: homeAddress,
    }),
  });
  let error = {};
  let resParsed = {};
  if(res.status === 200){
    returnresParsed = await res.json();
  } else{
    error = await res.json();
    throw error;
  }

  return resParsed;
}

export async function saveBooking(patientId, dentistId, date, timeStamp) {
  const res = await fetch("http://localhost:8080/booking/save", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: "Bearer " + getCookie("userData").jwt,
    },
    body: JSON.stringify({
      patient: {
        id: patientId 
      },
      dentist: {
        id: dentistId
      },
      bookingDate: date,
      timeSlot: timeStamp,
    }),
  });
  let error = {};
  let resParsed = {};
  if(res.status === 200){
    resParsed = await res.json();
  } else{
    error = await res.json();
    throw error;
  }
  return resParsed;
}


export async function findAllBookings() {
  const res = await fetch("http://localhost:8080/booking/findAll", {
    method: "GET",
    headers: {
      Authorization: "Bearer " + getCookie("userData").jwt,
    }
  });
  let error = {};
  let resParsed = {};
  if(res.status === 200){
    resParsed = await res.json();
  } else{
    error = await res.json();
    throw error;
  }
  return resParsed;
}

export async function findBookingById(id) {
  const res = await fetch("http://localhost:8080/booking/findById?id=" + id, {
    method: "GET",
    headers: {
      Authorization: "Bearer " + getCookie("userData").jwt,
    }
  });
  let error = {};
  let resParsed = {};
  if(res.status === 200){
    resParsed = await res.json();
  } else{
    error = await res.json();
    throw error;
  }
  return resParsed;
}



// try {
//   const dentist = await saveDentist("John", "Doe", "1234567890"); 
//   document.querySelector(".dentisInfo").innerHTML = `name: ${dentist.name} \n surname: ${dentist.surname} \n licenseNumber: ${dentist.licenseNumber}`; 
// } catch (error) {
//   document.querySelector(".dentisInfo").innerHTML = error.message;
//   document.querySelector(".dentisInfo").style.color = "red";
// }

// try {
//   const booking = await saveBooking("5e8667a4-39c6-3f51-85dd-2fcbecf02209", 'e807f1fc-f82d-332f-9bb0-18ca6738a19f', "2022-01-01", 3);

//   console.log(booking);
//   document.querySelector(".bookingInfo").innerHTML = `patientId: ${booking.patient.id} \n dentistId: ${booking.dentist.id} \n bookingDate: ${booking.bookingDate} \n timeSlot: ${booking.timeSlot}`;
// } catch (error) {
//   document.querySelector(".bookingInfo").innerHTML = error.message;
//   document.querySelector(".bookingInfo").style.color = "red";
// }


// try {
//   const dentists = await findAllDentists();

//   document.querySelector(".dentisInfo").innerHTML = dentists.map(element => `dentist: ${element.name} \n licenseNumber: ${element.licenseNumber}`);

// } catch (error) {
//   document.querySelector(".dentisInfo").innerHTML = error.message;
// }

// try {
//   const dentist = await findDentistById("e807f1fc-f82d-332f-9bb0-18ca6738a19f");

//   document.querySelector(".dentisInfo").innerHTML += ` </br> dentist: ${dentist.name} \n licenseNumber: ${dentist.licenseNumber}`;
// } catch (error) {
//   document.querySelector(".dentisInfo").innerHTML = error.message;
// }

// try {
//   const booking = await findBookingById("e441c61a-34dd-369f-a329-17b0988201d2	");
//   const dentist = await findDentistById(booking.dentist.id);

//   document.querySelector(".bookingInfo").innerHTML += ` </br> i'm: ${booking.patient.id} \n </br>
//   dentist: ${`name: ${dentist.name} \n surname: ${dentist.surname} \n licenseNumber: ${dentist.licenseNumber}`} \n </br>
//   bookingDate: ${booking.bookingDate} </br>
//   timeSlot: ${booking.timeSlot}`;
// } catch (error) {
//   document.querySelector(".bookingInfo").innerHTML = error.message;
// }