import "./RecentActivity.css";

import { useEffect, useState } from "react";

import {
  FaArrowDown,
  FaArrowUp,
  FaExchangeAlt
} from "react-icons/fa";

import { getAllTransactions } from "../../services/transactionService";

function RecentActivity() {

  const [activities, setActivities] = useState([]);

  useEffect(() => {

    async function loadActivities() {

      try {

        const transactions = await getAllTransactions();

        const latest = [...transactions]
          .sort(
            (a, b) =>
              new Date(b.transactionDate) -
              new Date(a.transactionDate)
          )
          .slice(0, 5);

        setActivities(latest);

      } catch (error) {

        console.error("Unable to load activities", error);

      }

    }

    loadActivities();

  }, []);

  function getIcon(type) {

    switch (type) {

      case "DEPOSIT":
        return <FaArrowDown />;

      case "WITHDRAW":
        return <FaArrowUp />;

      case "TRANSFER":
        return <FaExchangeAlt />;

      default:
        return <FaExchangeAlt />;
    }

  }

  return (

    <div className="activity-card">

      <h2>Recent Activity</h2>

      {activities.map((activity) => (

        <div
          className="activity-item"
          key={activity.id}
        >

          <div className="activity-icon">

            {getIcon(activity.transactionType)}

          </div>

          <div>

            <h4>

              {activity.transactionType} of ₹{activity.amount}

            </h4>

            <p>

              {new Date(
                activity.transactionDate
              ).toLocaleString()}

            </p>

          </div>

        </div>

      ))}

    </div>

  );

}

export default RecentActivity;