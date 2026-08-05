import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { addCustomer } from "../../services/customerService";
import "./AddCustomer.css";

function AddCustomer() {
    const navigate = useNavigate();

    const [customer, setCustomer] = useState({
        name: "",
        address: "",
        kycStatus: "Verified"
    });

    const handleChange = (e) => {
        const { name, value } = e.target;

        setCustomer({
            ...customer,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await addCustomer(customer);

            alert("Customer Added Successfully!");

            navigate("/");
        } catch (error) {
            console.error(error);
            alert("Unable to add customer");
        }
    };

    return (
        <div className="add-customer-container">
            <h2>Add Customer</h2>

            <form onSubmit={handleSubmit}>

                <div className="form-group">
                    <label>Customer Name</label>
                    <input
                        type="text"
                        name="name"
                        value={customer.name}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>Address</label>
                    <input
                        type="text"
                        name="address"
                        value={customer.address}
                        onChange={handleChange}
                        required
                    />
                </div>

                <div className="form-group">
                    <label>KYC Status</label>

                    <select
                        name="kycStatus"
                        value={customer.kycStatus}
                        onChange={handleChange}
                    >
                        <option value="Verified">Verified</option>
                        <option value="Pending">Pending</option>
                        <option value="Rejected">Rejected</option>
                    </select>
                </div>

                <button type="submit">
                    Add Customer
                </button>

            </form>
        </div>
    );
}

export default AddCustomer;