import "./QuickActions.css";

import {
  FaUserPlus,
  FaUniversity,
  FaExchangeAlt,
  FaSearch
} from "react-icons/fa";

import { useNavigate } from "react-router-dom";

function QuickActions() {

  const navigate = useNavigate();

  return (

    <div className="quick-actions">

      {/* Add Customer */}
      <div
        className="action-card"
        onClick={() => navigate("/add-customer")}
        style={{ cursor: "pointer" }}
      >
        <FaUserPlus />
        <h3>Add Customer</h3>
      </div>

      {/* Create Account */}
      <div
        className="action-card"
        onClick={() => navigate("/create-account")}
        style={{ cursor: "pointer" }}
      >
        <FaUniversity />
        <h3>Create Account</h3>
      </div>

      {/* Transfer Money */}
      <div
        className="action-card"
        onClick={() => navigate("/transactions")}
        style={{ cursor: "pointer" }}
      >
        <FaExchangeAlt />
        <h3>Transactions</h3>
      </div>

      {/* Search Customers */}
      <div
        className="action-card"
        onClick={() => navigate("/customers")}
        style={{ cursor: "pointer" }}
      >
        <FaSearch />
        <h3>Search Customers</h3>
      </div>

    </div>

  );
}

export default QuickActions;