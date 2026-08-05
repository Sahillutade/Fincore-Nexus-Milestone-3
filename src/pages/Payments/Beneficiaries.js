import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import MainLayout from "../../layouts/MainLayout";
import "./Beneficiaries.css";

import {
  getAllBeneficiaries,
  addBeneficiary,
} from "../../services/paymentService";

function Beneficiaries() {
  const navigate = useNavigate();

  const [beneficiaries, setBeneficiaries] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [message, setMessage] = useState("");

  const [formData, setFormData] = useState({
    name: "",
    accountNumber: "",
    confirmAccountNumber: "",
    ifscCode: "",
  });

  // Load beneficiaries from backend
  const loadBeneficiaries = async () => {
    try {
      const response = await getAllBeneficiaries();
      setBeneficiaries(response.data || []);
    } catch (error) {
      console.error("Failed to load beneficiaries:", error);
    }
  };

  useEffect(() => {
    loadBeneficiaries();
  }, []);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  // Add beneficiary to backend
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      formData.accountNumber !==
      formData.confirmAccountNumber
    ) {
      setMessage("Account numbers do not match.");
      return;
    }

    try {
      await addBeneficiary({
        name: formData.name,
        accountNumber: formData.accountNumber,
        ifscCode: formData.ifscCode,
        verified: true,
      });

      setFormData({
        name: "",
        accountNumber: "",
        confirmAccountNumber: "",
        ifscCode: "",
      });

      setMessage("Beneficiary added successfully.");
      setShowForm(false);

      loadBeneficiaries();

    } catch (error) {
      console.error("Failed to add beneficiary:", error);
      setMessage("Failed to add beneficiary.");
    }
  };

  return (
    <MainLayout>

      <div className="beneficiary-page">

        {/* Header */}

        <div className="beneficiary-header">

          <div>
            <h1>Beneficiary Management</h1>
            <p>Manage beneficiaries for fund transfers.</p>
          </div>

          <div className="beneficiary-actions">

            <button
              className="back-payment-btn"
              onClick={() => navigate("/payments")}
            >
              Back
            </button>

            <button
              className="add-beneficiary-btn"
              onClick={() => {
                setShowForm(!showForm);
                setMessage("");
              }}
            >
              Add Beneficiary
            </button>

          </div>

        </div>

        {/* Message */}

        {message && (
          <div className="beneficiary-message">
            {message}
          </div>
        )}

        {/* Add Beneficiary Form */}

        {showForm && (

          <div className="beneficiary-form-card">

            <h2>Add Beneficiary</h2>

            <form onSubmit={handleSubmit}>

              <div className="beneficiary-form-grid">

                <div className="beneficiary-form-group">

                  <label>Beneficiary Name</label>

                  <input
                    type="text"
                    name="name"
                    value={formData.name}
                    onChange={handleChange}
                    placeholder="Enter beneficiary name"
                    required
                  />

                </div>

                <div className="beneficiary-form-group">

                  <label>Account Number</label>

                  <input
                    type="text"
                    name="accountNumber"
                    value={formData.accountNumber}
                    onChange={handleChange}
                    placeholder="Enter account number"
                    required
                  />

                </div>

                <div className="beneficiary-form-group">

                  <label>Confirm Account Number</label>

                  <input
                    type="text"
                    name="confirmAccountNumber"
                    value={formData.confirmAccountNumber}
                    onChange={handleChange}
                    placeholder="Re-enter account number"
                    required
                  />

                </div>

                <div className="beneficiary-form-group">

                  <label>IFSC Code</label>

                  <input
                    type="text"
                    name="ifscCode"
                    value={formData.ifscCode}
                    onChange={handleChange}
                    placeholder="Example: FINB0001001"
                    required
                  />

                </div>

              </div>

              <button
                type="submit"
                className="save-beneficiary-btn"
              >
                Add Beneficiary
              </button>

            </form>

          </div>

        )}

        {/* Beneficiary Table */}

        <div className="beneficiary-table-card">

          <h2>Saved Beneficiaries</h2>

          <table className="beneficiary-table">

            <thead>

              <tr>
                <th>Name</th>
                <th>Account Number</th>
                <th>IFSC</th>
                <th>Status</th>
              </tr>

            </thead>

            <tbody>

              {beneficiaries.length === 0 ? (

                <tr>
                  <td
                    colSpan="4"
                    className="no-beneficiaries"
                  >
                    No beneficiaries added
                  </td>
                </tr>

              ) : (

                beneficiaries.map((beneficiary) => (

                  <tr key={beneficiary.benId}>

                    <td>{beneficiary.name}</td>

                    <td>
                      {beneficiary.accountNumber}
                    </td>

                    <td>
                      {beneficiary.ifscCode}
                    </td>

                    <td>

                      <span
                        className={
                          beneficiary.verified
                            ? "active-status"
                            : "pending-status"
                        }
                      >
                        {beneficiary.verified
                          ? "VERIFIED"
                          : "PENDING"}
                      </span>

                    </td>

                  </tr>

                ))

              )}

            </tbody>

          </table>

        </div>

      </div>

    </MainLayout>
  );
}

export default Beneficiaries;