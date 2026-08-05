import "./KYCCards.css";
import {
  FaIdCard,
  FaCheckCircle,
  FaClock,
  FaTimesCircle,
} from "react-icons/fa";

function KYCCards({
  total,
  verified,
  pending,
  rejected,
}) {
  return (
    <div className="kyc-cards">

      <div className="kyc-card">
        <FaIdCard className="kyc-icon total-icon" />
        <div>
          <p>Total KYC</p>
          <h2>{total}</h2>
        </div>
      </div>

      <div className="kyc-card">
        <FaCheckCircle className="kyc-icon verified-icon" />
        <div>
          <p>Verified</p>
          <h2>{verified}</h2>
        </div>
      </div>

      <div className="kyc-card">
        <FaClock className="kyc-icon pending-icon" />
        <div>
          <p>Pending</p>
          <h2>{pending}</h2>
        </div>
      </div>

      <div className="kyc-card">
        <FaTimesCircle className="kyc-icon rejected-icon" />
        <div>
          <p>Rejected</p>
          <h2>{rejected}</h2>
        </div>
      </div>

    </div>
  );
}

export default KYCCards;