import "./Sidebar.css";

import { NavLink } from "react-router-dom";

import {
  FaHome,
  FaUsers,
  FaUniversity,
  FaExchangeAlt,
  FaFileInvoiceDollar,
  FaMoneyCheckAlt,
  FaIdCard,
  FaCog,
} from "react-icons/fa";

function Sidebar() {

  return (

    <aside className="sidebar">

      <div className="sidebar-heading">
        <span>MAIN MENU</span>
      </div>


      <nav className="sidebar-menu">

        <NavLink to="/" end>

          <FaHome className="sidebar-icon" />

          <span>Dashboard</span>

        </NavLink>


        <NavLink to="/accounts">

          <FaUniversity className="sidebar-icon" />

          <span>Accounts</span>

        </NavLink>


        <NavLink to="/customers">

          <FaUsers className="sidebar-icon" />

          <span>Customers</span>

        </NavLink>


        <NavLink to="/transactions">

          <FaExchangeAlt className="sidebar-icon" />

          <span>Transactions</span>

        </NavLink>


        <NavLink to="/loans">

          <FaFileInvoiceDollar className="sidebar-icon" />

          <span>Loans</span>

        </NavLink>


        <NavLink to="/payments">

          <FaMoneyCheckAlt className="sidebar-icon" />

          <span>Payments</span>

        </NavLink>


        <NavLink to="/kyc">

          <FaIdCard className="sidebar-icon" />

          <span>KYC</span>

        </NavLink>

      </nav>


      {/* Bottom Settings */}

      <div className="sidebar-bottom">

        <NavLink to="/settings">

          <FaCog className="sidebar-icon" />

          <span>Settings</span>

        </NavLink>

      </div>

    </aside>

  );

}

export default Sidebar;