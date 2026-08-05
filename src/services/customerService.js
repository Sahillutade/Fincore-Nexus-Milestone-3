import API from "./api";

// Get All Customers
export async function getAllCustomers() {

    const response = await fetch(`${API.CUSTOMER}/customer/all`);

    if (!response.ok) {
        throw new Error("Unable to fetch customers");
    }

    return await response.json();
}

// Add Customer
export async function addCustomer(customer) {

    const response = await fetch(`${API.CUSTOMER}/customer/add`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(customer)
    });

    if (!response.ok) {
        throw new Error("Unable to add customer");
    }

    return await response.json();
}