import "./DashboardCard.css";
import {
  FaArrowUp,
  FaArrowDown,
} from "react-icons/fa";

function DashboardCard({
  title,
  value,
  color,
  icon,
  trend = "0%",
}) {

  const isPositive =
    !trend.startsWith("-") && trend !== "Live";

  return (

    <div
      className="dashboard-card"
      style={{
        borderTop: `5px solid ${color}`,
      }}
    >

      <div className="card-header">

        <div
          className="card-icon"
          style={{
            color: color,
          }}
        >
          {icon}
        </div>

        <span
          className={`trend ${
            trend === "Live"
              ? "live"
              : isPositive
              ? "positive"
              : "negative"
          }`}
        >

          {trend !== "Live" &&
            (isPositive ? (
              <FaArrowUp />
            ) : (
              <FaArrowDown />
            ))}

          {trend}

        </span>

      </div>

      <h3>{title}</h3>

      <h1>{value}</h1>

    </div>

  );

}

export default DashboardCard;