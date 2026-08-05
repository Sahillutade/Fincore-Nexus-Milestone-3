import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import MainLayout from "../../layouts/MainLayout";
import "./Payments.css";

import { getAllPayments } from "../../services/paymentService";

import {
  FaExchangeAlt,
  FaCheckCircle,
  FaClock,
  FaRupeeSign,
} from "react-icons/fa";

function Payments() {
  const navigate = useNavigate();

  const [payments, setPayments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState("");
  const [statusFilter, setStatusFilter] = useState("ALL");

  // Load payments from backend
  useEffect(() => {
    const loadPayments = async () => {
      try {
        const response = await getAllPayments();
        setPayments(response.data || []);
      } catch (error) {
        console.error("Failed to load payments:", error);
      } finally {
        setLoading(false);
      }
    };

    loadPayments();
  }, []);

  // Dashboard calculations
  const totalPayments = payments.length;

  const successfulPayments = payments.filter(
    (payment) => payment.status === "SUCCESS"
  ).length;

  const pendingPayments = payments.filter(
    (payment) => payment.status === "PENDING"
  ).length;

  const today = new Date().toDateString();

  const todaysValue = payments
    .filter(
      (payment) =>
        payment.timestamp &&
        new Date(payment.timestamp).toDateString() === today &&
        payment.status === "SUCCESS"
    )
    .reduce(
      (total, payment) => total + Number(payment.amount || 0),
      0
    );

  // Search + Status Filter
  const filteredPayments = payments.filter((payment) => {
    const searchValue = search.toLowerCase();

    const matchesSearch =
      payment.toAccountId?.toLowerCase().includes(searchValue) ||
      payment.fromAccountId?.toLowerCase().includes(searchValue) ||
      payment.paymentType?.toLowerCase().includes(searchValue) ||
      payment.utrNumber?.toLowerCase().includes(searchValue) ||
      payment.paymentId?.toString().includes(searchValue);

    const matchesStatus =
      statusFilter === "ALL" ||
      payment.status === statusFilter;

    return matchesSearch && matchesStatus;
  });

  return (
    <MainLayout>
      <div className="payments-page">

        {/* Header */}
        <div className="payments-header">
          <div>
            <h1>Payments & Transfers</h1>

            <p>
              Manage fund transfers, beneficiaries and payment transactions.
            </p>
          </div>

          <div className="payments-actions">
            <button
              className="beneficiary-btn"
              onClick={() => navigate("/payments/beneficiaries")}
            >
              Beneficiaries
            </button>

            <button
              className="new-transfer-btn"
              onClick={() => navigate("/payments/new")}
            >
              New Transfer
            </button>
          </div>
        </div>

        {/* Dashboard Cards */}
        <div className="payment-cards">

          <div className="payment-card">
            <FaExchangeAlt className="payment-icon" />

            <div>
              <p>Total Payments</p>
              <h2>{totalPayments}</h2>
            </div>
          </div>

          <div className="payment-card">
            <FaCheckCircle className="payment-icon" />

            <div>
              <p>Successful</p>
              <h2>{successfulPayments}</h2>
            </div>
          </div>

          <div className="payment-card">
            <FaClock className="payment-icon" />

            <div>
              <p>Pending</p>
              <h2>{pendingPayments}</h2>
            </div>
          </div>

          <div className="payment-card">
            <FaRupeeSign className="payment-icon" />

            <div>
              <p>Today's Value</p>

              <h2>
                ₹{todaysValue.toLocaleString("en-IN")}
              </h2>
            </div>
          </div>

        </div>

        {/* Recent Payments */}
        <div className="payments-section">

          <div className="payment-history-header">
            <h2>Recent Payments</h2>

            <div className="payment-filters">

              <input
                type="text"
                placeholder="Search payments..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
              />

              <select
                value={statusFilter}
                onChange={(e) => setStatusFilter(e.target.value)}
              >
                <option value="ALL">All Status</option>
                <option value="SUCCESS">Success</option>
                <option value="PENDING">Pending</option>
                <option value="FAILED">Failed</option>
                <option value="FAILED_FRAUD_RISK">
                  Fraud Risk
                </option>
              </select>

            </div>
          </div>

          <div className="payments-table-wrapper">

            <table className="payments-table">

              <thead>
                <tr>
                  <th>Payment ID</th>
                  <th>To Account</th>
                  <th>Type</th>
                  <th>Amount</th>
                  <th>Status</th>
                  <th>Date</th>
                </tr>
              </thead>

              <tbody>

                {loading ? (

                  <tr>
                    <td colSpan="6" className="no-payments">
                      Loading payments...
                    </td>
                  </tr>

                ) : filteredPayments.length === 0 ? (

                  <tr>
                    <td colSpan="6" className="no-payments">
                      No payments available
                    </td>
                  </tr>

                ) : (

                  filteredPayments.map((payment) => (

                    <tr key={payment.paymentId}>

                      <td className="payment-id">
                        PAY-
                        {String(payment.paymentId).padStart(
                          5,
                          "0"
                        )}
                      </td>

                      <td>
                        {payment.toAccountId}
                      </td>

                      <td>
                        <span className="transfer-type">
                          {payment.paymentType}
                        </span>
                      </td>

                      <td className="payment-amount">
                        ₹
                        {Number(
                          payment.amount
                        ).toLocaleString("en-IN")}
                      </td>

                      <td>
                        <span
                          className={`payment-status ${payment.status
                            ?.toLowerCase()
                            .replaceAll("_", "-")}`}
                        >
                          {payment.status}
                        </span>
                      </td>

                      <td>
                        {payment.timestamp
                          ? new Date(
                              payment.timestamp
                            ).toLocaleString("en-IN")
                          : "-"}
                      </td>

                    </tr>

                  ))

                )}

              </tbody>

            </table>

          </div>

        </div>

      </div>
    </MainLayout>
  );
}

export default Payments;