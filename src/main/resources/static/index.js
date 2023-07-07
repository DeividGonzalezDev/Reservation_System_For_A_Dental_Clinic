import {
  saveDentist,
  findAllDentists,
  saveBooking,
  findAllBookings,
} from "./fetchs.js";
import { getCookie } from "./utilsFunctions.js";
import { showAllUserBookings } from "./components/showAllUserBookings.js";
import { homePage } from "./components/homePage.js";
import { showAllDentits } from "./components/showAllDentists.js";

// selectors
const bookingButton = document.querySelector(".booking-button");
const odontologosMenu = document.querySelector(".odontologos-menu");
const defaultModal = document.getElementById("defaultModal");
const backgroundModal = document.querySelector(".background-modal");
const closeModal = document.getElementById("closeModal");
const dentistsList = document.getElementById("dentistsList");
const bookingSubmit = document.getElementById("bookingSubmit");
const bookingForm = document.getElementById("bookingForm");
const principalBanner = document.getElementById("principal-banner");
const ourDentists = document.getElementById("our-dentists");
const showUserBookings = document.getElementById("show-user-bookings");
const mainContainer = document.getElementById("main-container");
const homePageMenu = document.getElementById("home");
const dentistMenu = document.getElementById("odontologos-menu");
const userRole = document.querySelector(".user-role");

// Event Listeners
//abre el pop-up de reserva
bookingButton.addEventListener("click", () => {
  if(getCookie("userData") !== null){
    defaultModal.classList.toggle("hidden");
    backgroundModal.classList.toggle("hidden");
  }else{
    window.location.href = "/auth";
  }
});

closeModal.addEventListener("click", () => {
  defaultModal.classList.toggle("hidden");
  backgroundModal.classList.toggle("hidden");
});

//verfifica si es un usuario Admin
function isAdmin() {
  let cookieValue = getCookie("userData");
  if(cookieValue && cookieValue.appUserRole == "ADMIN"){
    return true;
  }else{
    return false;
  }
}

document.addEventListener("DOMContentLoaded",  () => {
  if(isAdmin() == false){
    dentistMenu.parentNode.remove();
    const userBookingsLink = document.createElement('a');
    userBookingsLink.id = 'user-bookings';
    userBookingsLink.href = getCookie("userData") !== null ? '#' : '/auth';
    userBookingsLink.className = 'text-white bg-primary-700 hover:bg-primary-800 focus:ring-4 focus:ring-primary-300 font-medium rounded-lg text-sm px-4 lg:px-5 py-2 lg:py-2.5 mr-2 dark:bg-primary-600 dark:hover:bg-primary-700 focus:outline-none dark:focus:ring-primary-800 admin-dashboard-button';
    userBookingsLink.innerText = getCookie("userData") !== null ? 'Mis Reservas' : 'Inicia Sesión';
    
    showUserBookings.parentNode.replaceChild(userBookingsLink, showUserBookings);
    userRole.innerText =getCookie("userData") !== null ?  getCookie("userData").name : "User";
  };
})


//Agrega los odontologos al DentistsList de el Modal de reservaciones
document.addEventListener("DOMContentLoaded", async () => {
  console.log("ok");
  if(getCookie("userData") !== null){
    const dentists = await findAllDentists();
    dentists.forEach((dentist) => {
      const option = document.createElement("option");
      option.value = dentist.id;
      option.innerHTML = dentist.name;
      dentistsList.appendChild(option);
    });
  }
});


bookingSubmit.addEventListener("click", async (event) => {
  event.preventDefault();
  console.log(bookingForm);
  const dateValue = bookingForm.date.value;
  const timeSlotValue = bookingForm.timeSlot.value;
  const dentistValue = dentistsList.value;
  //voy a hardcodear el id del paciente por temas de comlejidad
  try {
    const savedBooking = await saveBooking(
      "5e8667a4-39c6-3f51-85dd-2fcbecf02209",
      dentistValue,
      dateValue,
      timeSlotValue
    );
    alert("Reserva realizada correctamente");
    defaultModal.classList.toggle("hidden");
    backgroundModal.classList.toggle("hidden");
  } catch (error) {
    alert(error.message);
  }
});

showUserBookings.addEventListener("click", async () => {
  mainContainer.innerHTML = showAllUserBookings();
  const bookingTable = document.getElementById("booking-table-body");

  try {
    const bookings = await findAllBookings();
  
    bookings.map((booking) => {
      bookingTable.innerHTML += `<tr class="border-b dark:border-gray-700">
                              <th scope="row" class="px-4 py-3 font-medium text-gray-900 whitespace-nowrap dark:text-white">${booking.creationDate}</th>
                              <td class="px-4 py-3">${booking.dentist.name}</td>
                              <td class="px-4 py-3">${booking.patient.name}</td>
                              <td class="px-4 py-3 max-w-[12rem] truncate">${booking.bookingDate}</td>
                              <td class="px-4 py-3">${booking.timeSlot}</td>
                              <td class="px-4 py-3 flex items-center justify-end">
                                  <button id="apple-imac-27-dropdown-button" data-dropdown-toggle="apple-imac-27-dropdown" class="inline-flex items-center text-sm font-medium hover:bg-gray-100 dark:hover:bg-gray-700 p-1.5 dark:hover-bg-gray-800 text-center text-gray-500 hover:text-gray-800 rounded-lg focus:outline-none dark:text-gray-400 dark:hover:text-gray-100" type="button">
                                      <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewbox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                          <path d="M6 10a2 2 0 11-4 0 2 2 0 014 0zM12 10a2 2 0 11-4 0 2 2 0 014 0zM16 12a2 2 0 100-4 2 2 0 000 4z" />
                                      </svg>
                                  </button>
                                  <div id="apple-imac-27-dropdown" class="hidden z-10 w-44 bg-white rounded divide-y divide-gray-100 shadow dark:bg-gray-700 dark:divide-gray-600">
                                      <ul class="py-1 text-sm" aria-labelledby="apple-imac-27-dropdown-button">
                                          <li>
                                              <button type="button" data-modal-target="updateProductModal" data-modal-toggle="updateProductModal" class="flex w-full items-center py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white text-gray-700 dark:text-gray-200">
                                                  <svg class="w-4 h-4 mr-2" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                                      <path d="M17.414 2.586a2 2 0 00-2.828 0L7 10.172V13h2.828l7.586-7.586a2 2 0 000-2.828z" />
                                                      <path fill-rule="evenodd" clip-rule="evenodd" d="M2 6a2 2 0 012-2h4a1 1 0 010 2H4v10h10v-4a1 1 0 112 0v4a2 2 0 01-2 2H4a2 2 0 01-2-2V6z" />
                                                  </svg>
                                                  Edit
                                              </button>
                                          </li>
                                          <li>
                                              <button type="button" data-modal-target="readProductModal" data-modal-toggle="readProductModal" class="flex w-full items-center py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white text-gray-700 dark:text-gray-200">
                                                  <svg class="w-4 h-4 mr-2" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                                      <path d="M10 12a2 2 0 100-4 2 2 0 000 4z" />
                                                      <path fill-rule="evenodd" clip-rule="evenodd" d="M.458 10C1.732 5.943 5.522 3 10 3s8.268 2.943 9.542 7c-1.274 4.057-5.064 7-9.542 7S1.732 14.057.458 10zM14 10a4 4 0 11-8 0 4 4 0 018 0z" />
                                                  </svg>
                                                  Preview
                                              </button>
                                          </li>
                                          <li>
                                              <button type="button" data-modal-target="deleteModal" data-modal-toggle="deleteModal" class="flex w-full items-center py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600 text-red-500 dark:hover:text-red-400">
                                                  <svg class="w-4 h-4 mr-2" viewbox="0 0 14 15" fill="none" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
                                                      <path fill-rule="evenodd" clip-rule="evenodd" fill="currentColor" d="M6.09922 0.300781C5.93212 0.30087 5.76835 0.347476 5.62625 0.435378C5.48414 0.523281 5.36931 0.649009 5.29462 0.798481L4.64302 2.10078H1.59922C1.36052 2.10078 1.13161 2.1956 0.962823 2.36439C0.79404 2.53317 0.699219 2.76209 0.699219 3.00078C0.699219 3.23948 0.79404 3.46839 0.962823 3.63718C1.13161 3.80596 1.36052 3.90078 1.59922 3.90078V12.9008C1.59922 13.3782 1.78886 13.836 2.12643 14.1736C2.46399 14.5111 2.92183 14.7008 3.39922 14.7008H10.5992C11.0766 14.7008 11.5344 14.5111 11.872 14.1736C12.2096 13.836 12.3992 13.3782 12.3992 12.9008V3.90078C12.6379 3.90078 12.8668 3.80596 13.0356 3.63718C13.2044 3.46839 13.2992 3.23948 13.2992 3.00078C13.2992 2.76209 13.2044 2.53317 13.0356 2.36439C12.8668 2.1956 12.6379 2.10078 12.3992 2.10078H9.35542L8.70382 0.798481C8.62913 0.649009 8.5143 0.523281 8.37219 0.435378C8.23009 0.347476 8.06631 0.30087 7.89922 0.300781H6.09922ZM4.29922 5.70078C4.29922 5.46209 4.39404 5.23317 4.56282 5.06439C4.73161 4.8956 4.96052 4.80078 5.19922 4.80078C5.43791 4.80078 5.66683 4.8956 5.83561 5.06439C6.0044 5.23317 6.09922 5.46209 6.09922 5.70078V11.1008C6.09922 11.3395 6.0044 11.5684 5.83561 11.7372C5.66683 11.906 5.43791 12.0008 5.19922 12.0008C4.96052 12.0008 4.73161 11.906 4.56282 11.7372C4.39404 11.5684 4.29922 11.3395 4.29922 11.1008V5.70078ZM8.79922 4.80078C8.56052 4.80078 8.33161 4.8956 8.16282 5.06439C7.99404 5.23317 7.89922 5.46209 7.89922 5.70078V11.1008C7.89922 11.3395 7.99404 11.5684 8.16282 11.7372C8.33161 11.906 8.56052 12.0008 8.79922 12.0008C9.03791 12.0008 9.26683 11.906 9.43561 11.7372C9.6044 11.5684 9.69922 11.3395 9.69922 11.1008V5.70078C9.69922 5.46209 9.6044 5.23317 9.43561 5.06439C9.26683 4.8956 9.03791 4.80078 8.79922 4.80078Z" />
                                                  </svg>
                                                  Delete
                                              </button>
                                          </li>
                                      </ul>
                                  </div>
                              </td>
                          </tr>`;
    });
    
  } catch (error) {
    alert(error);
  }

  

  
  dentistMenu.addEventListener("click", async () => {
    mainContainer.innerHTML = showAllDentits();
    const dentistsTable = document.getElementById("dentists-table-body");
    const newDentistModalButton = document.getElementById("newDentistModalButton");

    console.log(newDentistModalButton);
    newDentistModalButton.addEventListener("click", newDentistModal)
    
    try {
      const dentists = await findAllDentists();
      
      dentists.map((dentist) => {
        dentistsTable.innerHTML += `<tr class="border-b dark:border-gray-700">
        <th scope="row" class="px-4 py-3 font-medium text-gray-900 whitespace-nowrap dark:text-white">${dentist.name}</th>
        <td class="px-4 py-3">${dentist.surname}</td>
                              <td class="px-4 py-3">${dentist.licenseNumber}</td>
                              <td class="px-4 py-3 max-w-[12rem] truncate">${dentist.homeAddress === null ? "No tiente una dirección añadida" : dentist.homeAddress.address}</td>
                              <td class="px-4 py-3 flex items-center justify-end">
                                  <button id="apple-imac-27-dropdown-button" data-dropdown-toggle="apple-imac-27-dropdown" class="inline-flex items-center text-sm font-medium hover:bg-gray-100 dark:hover:bg-gray-700 p-1.5 dark:hover-bg-gray-800 text-center text-gray-500 hover:text-gray-800 rounded-lg focus:outline-none dark:text-gray-400 dark:hover:text-gray-100" type="button">
                                      <svg class="w-5 h-5" aria-hidden="true" fill="currentColor" viewbox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                          <path d="M6 10a2 2 0 11-4 0 2 2 0 014 0zM12 10a2 2 0 11-4 0 2 2 0 014 0zM16 12a2 2 0 100-4 2 2 0 000 4z" />
                                      </svg>
                                  </button>
                                  <div id="apple-imac-27-dropdown" class="hidden z-10 w-44 bg-white rounded divide-y divide-gray-100 shadow dark:bg-gray-700 dark:divide-gray-600">
                                      <ul class="py-1 text-sm" aria-labelledby="apple-imac-27-dropdown-button">
                                          <li>
                                              <button type="button" data-modal-target="updateProductModal" data-modal-toggle="updateProductModal" class="flex w-full items-center py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white text-gray-700 dark:text-gray-200">
                                                  <svg class="w-4 h-4 mr-2" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                                      <path d="M17.414 2.586a2 2 0 00-2.828 0L7 10.172V13h2.828l7.586-7.586a2 2 0 000-2.828z" />
                                                      <path fill-rule="evenodd" clip-rule="evenodd" d="M2 6a2 2 0 012-2h4a1 1 0 010 2H4v10h10v-4a1 1 0 112 0v4a2 2 0 01-2 2H4a2 2 0 01-2-2V6z" />
                                                  </svg>
                                                  Edit
                                              </button>
                                          </li>
                                          <li>
                                              <button type="button" data-modal-target="readProductModal" data-modal-toggle="readProductModal" class="flex w-full items-center py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white text-gray-700 dark:text-gray-200">
                                                  <svg class="w-4 h-4 mr-2" xmlns="http://www.w3.org/2000/svg" viewbox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                                      <path d="M10 12a2 2 0 100-4 2 2 0 000 4z" />
                                                      <path fill-rule="evenodd" clip-rule="evenodd" d="M.458 10C1.732 5.943 5.522 3 10 3s8.268 2.943 9.542 7c-1.274 4.057-5.064 7-9.542 7S1.732 14.057.458 10zM14 10a4 4 0 11-8 0 4 4 0 018 0z" />
                                                  </svg>
                                                  Preview
                                              </button>
                                          </li>
                                          <li>
                                              <button type="button" data-modal-target="deleteModal" data-modal-toggle="deleteModal" class="flex w-full items-center py-2 px-4 hover:bg-gray-100 dark:hover:bg-gray-600 text-red-500 dark:hover:text-red-400">
                                                  <svg class="w-4 h-4 mr-2" viewbox="0 0 14 15" fill="none" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
                                                      <path fill-rule="evenodd" clip-rule="evenodd" fill="currentColor" d="M6.09922 0.300781C5.93212 0.30087 5.76835 0.347476 5.62625 0.435378C5.48414 0.523281 5.36931 0.649009 5.29462 0.798481L4.64302 2.10078H1.59922C1.36052 2.10078 1.13161 2.1956 0.962823 2.36439C0.79404 2.53317 0.699219 2.76209 0.699219 3.00078C0.699219 3.23948 0.79404 3.46839 0.962823 3.63718C1.13161 3.80596 1.36052 3.90078 1.59922 3.90078V12.9008C1.59922 13.3782 1.78886 13.836 2.12643 14.1736C2.46399 14.5111 2.92183 14.7008 3.39922 14.7008H10.5992C11.0766 14.7008 11.5344 14.5111 11.872 14.1736C12.2096 13.836 12.3992 13.3782 12.3992 12.9008V3.90078C12.6379 3.90078 12.8668 3.80596 13.0356 3.63718C13.2044 3.46839 13.2992 3.23948 13.2992 3.00078C13.2992 2.76209 13.2044 2.53317 13.0356 2.36439C12.8668 2.1956 12.6379 2.10078 12.3992 2.10078H9.35542L8.70382 0.798481C8.62913 0.649009 8.5143 0.523281 8.37219 0.435378C8.23009 0.347476 8.06631 0.30087 7.89922 0.300781H6.09922ZM4.29922 5.70078C4.29922 5.46209 4.39404 5.23317 4.56282 5.06439C4.73161 4.8956 4.96052 4.80078 5.19922 4.80078C5.43791 4.80078 5.66683 4.8956 5.83561 5.06439C6.0044 5.23317 6.09922 5.46209 6.09922 5.70078V11.1008C6.09922 11.3395 6.0044 11.5684 5.83561 11.7372C5.66683 11.906 5.43791 12.0008 5.19922 12.0008C4.96052 12.0008 4.73161 11.906 4.56282 11.7372C4.39404 11.5684 4.29922 11.3395 4.29922 11.1008V5.70078ZM8.79922 4.80078C8.56052 4.80078 8.33161 4.8956 8.16282 5.06439C7.99404 5.23317 7.89922 5.46209 7.89922 5.70078V11.1008C7.89922 11.3395 7.99404 11.5684 8.16282 11.7372C8.33161 11.906 8.56052 12.0008 8.79922 12.0008C9.03791 12.0008 9.26683 11.906 9.43561 11.7372C9.6044 11.5684 9.69922 11.3395 9.69922 11.1008V5.70078C9.69922 5.46209 9.6044 5.23317 9.43561 5.06439C9.26683 4.8956 9.03791 4.80078 8.79922 4.80078Z" />
                                                  </svg>
                                                  Delete
                                              </button>
                                          </li>
                                      </ul>
                                  </div>
                              </td>
                          </tr>`;
    });
  } catch (error) {
    alert(error.message);
    
  }
});


 function newDentistModal() {
  document.getElementById("defaultModalDentists").classList.toggle("hidden");
   backgroundModal.classList.toggle("hidden");
  const dentistSubmitButton = document.getElementById("dentistSubmit");
  dentistSubmitButton.addEventListener("click", submitDentistData)

  const closeModalDentists = document.getElementById("closeModalDentists");
  closeModalDentists.addEventListener("click", () => {
    document.getElementById("defaultModalDentists").classList.toggle("hidden");
    backgroundModal.classList.toggle("hidden");
  })
};

async function submitDentistData(event) {
  event.preventDefault();
  const dentistForm = document.getElementById("dentistForm");
  console.log(dentistForm);
  const nameValue = dentistForm.name.value;
  const surnameValue = dentistForm.surname.value;
  const licenseNumberValue = dentistForm.licenseNumber.value;
  const homeAddressValue = {
    address: dentistForm.homeAddress.value
  };
  //voy a hardcodear el id del paciente por temas de comlejidad
  try {
    const savedDentist = await saveDentist(
      nameValue,
      surnameValue,
      licenseNumberValue,
      homeAddressValue
    );
    alert("Odontólogo añadido correctamente");
    document.getElementById("defaultModalDentists").classList.toggle("hidden");
    backgroundModal.classList.toggle("hidden");
    dentistMenu.click();
  } catch (error) {
    alert(error.message);
  }
}

// document.getElementById('closeModalDentists').addEventListener("click", () => {
//   document.getElementById("defaultModalDentists").classList.toggle("hidden");
//   backgroundModal.classList.toggle("hidden");
// });
homePageMenu.addEventListener("click", () => {
    location.reload();
  })
});
