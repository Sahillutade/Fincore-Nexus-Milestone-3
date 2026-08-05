import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import MainLayout from "../../layouts/MainLayout";
import "./NewTransfer.css";

import {
  getAllBeneficiaries,
  makeTransfer,
} from "../../services/paymentService";

import { getAllAccounts } from "../../services/accountService";

function NewTransfer() {
  const navigate = useNavigate();

  const [accounts, setAccounts] = useState([]);
  const [beneficiaries, setBeneficiaries] = useState([]);
  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);

  const [formData, setFormData] = useState({
    fromAccount: "",
    beneficiaryId: "",
    amount: "",
    transferType: "IMPS",
    remarks: "",
  });

  // Load accounts + beneficiaries
  useEffect(() => {
    const loadData = async () => {
      try {
        const [accountResponse, beneficiaryResponse] =
          await Promise.all([
            getAllAccounts(),
            getAllBeneficiaries(),
          ]);

        setAccounts(accountResponse || []);
        setBeneficiaries(beneficiaryResponse.data || []);

      } catch (error) {
        console.error("Failed to load transfer data:", error);
        setMessage("Failed to load accounts or beneficiaries.");
      }
    };

    loadData();
  }, []);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const selectedBeneficiary = beneficiaries.find(
    (beneficiary) =>
      String(beneficiary.benId) === formData.beneficiaryId
  );

  // Transfer
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (Number(formData.amount) <= 0) {
      setMessage("Please enter a valid amount.");
      return;
    }

    if (!selectedBeneficiary) {
      setMessage("Please select a beneficiary.");
      return;
    }

    const transferData = {
      fromAccountId: formData.fromAccount,
      toAccountId: selectedBeneficiary.accountNumber,
      amount: Number(formData.amount),
      paymentType: formData.transferType,
    };

    try {
      setLoading(true);
      setMessage("");

      const response = await makeTransfer(transferData);

      if (response.data.status === "SUCCESS") {
        setMessage("Transfer completed successfully.");

        setTimeout(() => {
          navigate("/payments");
        }, 1000);
      } else {
        setMessage(`Transfer status: ${response.data.status}`);
      }

    } catch (error) {
      console.error("Transfer failed:", error);

      setMessage(
        error.response?.data?.message ||
        "Transfer failed. Please try again."
      );

    } finally {
      setLoading(false);
    }
  };

  return (
    <MainLayout>

      <div className="transfer-page">

        {/* Header */}

        <div className="transfer-header">

          <div>
            <h1>New Fund Transfer</h1>
            <p>Transfer funds using IMPS, NEFT or UPI.</p>
          </div>

          <button
            className="back-btn"
            onClick={() => navigate("/payments")}
          >
            Back to Payments
          </button>

        </div>

        <div className="transfer-card">

          {message && (
            <div className="transfer-message">
              {message}
            </div>
          )}

          <form onSubmit={handleSubmit}>

            {/* From Account */}

            <div className="form-group">

              <label>From Account</label>

              <select
                name="fromAccount"
                value={formData.fromAccount}
                onChange={handleChange}
                required
              >

                <option value="">
                  Select Account
                </option>

                {accounts.map((account) => (

                  <option
                    key={account.id}
                    value={account.accountNumber}
                  >
                    {account.accountNumber}
                    {account.accountType
                      ? ` - ${account.accountType}`
                      : ""}
                  </option>

                ))}

              </select>

            </div>

            {/* Beneficiary */}

            <div className="form-group">

              <label>Beneficiary</label>

              <select
                name="beneficiaryId"
                value={formData.beneficiaryId}
                onChange={handleChange}
                required
              >

                <option value="">
                  Select Beneficiary
                </option>

                {beneficiaries
                  .filter((beneficiary) => beneficiary.verified)
                  .map((beneficiary) => (

                    <option
                      key={beneficiary.benId}
                      value={beneficiary.benId}
                    >
                      {beneficiary.name} -{" "}
                      {beneficiary.accountNumber}
                    </option>

                  ))}

              </select>

            </div>

            {/* Beneficiary Details */}

            {selectedBeneficiary && (

              <div className="beneficiary-details">

                <h3>Beneficiary Details</h3>

                <p>
                  <strong>Name:</strong>{" "}
                  {selectedBeneficiary.name}
                </p>

                <p>
                  <strong>Account:</strong>{" "}
                  {selectedBeneficiary.accountNumber}
                </p>

                <p>
                  <strong>IFSC:</strong>{" "}
                  {selectedBeneficiary.ifscCode}
                </p>

                <p>
                  <strong>Status:</strong>{" "}
                  {selectedBeneficiary.verified
                    ? "Verified"
                    : "Pending"}
                </p>

              </div>

            )}

            {/* Transfer Type */}

            <div className="form-group">

              <label>Transfer Type</label>

              <select
                name="transferType"
                value={formData.transferType}
                onChange={handleChange}
              >
                <option value="IMPS">IMPS</option>
                <option value="NEFT">NEFT</option>
                <option value="UPI">UPI</option>
              </select>

            </div>

            {/* Amount */}

            <div className="form-group">

              <label>Amount</label>

              <input
                type="number"
                name="amount"
                value={formData.amount}
                onChange={handleChange}
                placeholder="Enter transfer amount"
                min="1"
                required
              />

            </div>

            {/* Remarks */}

            <div className="form-group">

              <label>Remarks</label>

              <textarea
                name="remarks"
                value={formData.remarks}
                onChange={handleChange}
                placeholder="Enter remarks (optional)"
                rows="3"
              />

            </div>

            <button
              type="submit"
              className="transfer-btn"
              disabled={loading}
            >
              {loading
                ? "Processing..."
                : "Proceed Transfer"}
            </button>

          </form>

        </div>

      </div>

    </MainLayout>
  );
}

export default NewTransfer;