import * as employeeController from './controllers/employee-controller.js';

window.addEventListener('DOMContentLoaded', () => {

    const submitBtn = document.getElementById('insert')
    const updateBtn = document.getElementById('update')
    const deleteBtn = document.getElementById('delete')    

    submitBtn.addEventListener('click', () => {
        const data = packForm();
        employeeController.insertEmployee(data);
    });

    updateBtn.addEventListener('click', () => {
        const data = packForm();
        employeeController.updateEmployee(data);
    });

    deleteBtn.addEventListener('click', () => {
        employeeController.deleteEmployee();
    });
})

function packForm() {
    return {
        firstname: document.querySelector('#inputFirstname').value,
        lastname: document.querySelector('#inputLastname').value,
        email: document.querySelector('#inputEmail').value,
        phoneNumber: document.querySelector('#phoneNumber').value
    }
}