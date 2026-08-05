import "./Navbar.css";

import {
  FaBell,
  FaMoon,
  FaUserCircle,
  FaUniversity,
  FaChevronDown,
} from "react-icons/fa";

function Navbar() {
  return (
    <nav className="navbar">

      {/* Brand */}
      <div className="navbar-brand">
        <div className="brand-icon">
          <FaUniversity />
        </div>

        <div className="brand-text">
          <span className="brand-name">FinCore Nexus</span>
          <span className="brand-subtitle">Digital Banking</span>
        </div>
      </div>

      {/* Right Section */}
      <div className="nav-right">

        <button
          className="nav-action"
          title="Notifications"
        >
          <FaBell />

          <span className="notification-dot"></span>
        </button>

        <button
          className="nav-action"
          title="Theme"
        >
          <FaMoon />
        </button>

        <div className="navbar-divider"></div>

        <div className="profile-section">

          <FaUserCircle className="profile-icon" />

          <div className="profile-info">
            <span className="profile-name">
              Admin
            </span>

            <span className="profile-role">
              Administrator
            </span>
          </div>

          <FaChevronDown className="profile-arrow" />

        </div>

      </div>

    </nav>
  );
}

export default Navbar;