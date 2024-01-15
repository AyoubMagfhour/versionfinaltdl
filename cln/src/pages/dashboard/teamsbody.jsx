import React, { useEffect, useState } from "react";
import "../../styles/bodyTable.css";
import GroupeService from "../../services/groupe-service";
import Task from "./task";
import { Link } from 'react-router-dom';
import UserService from '../../services/user-service' 



const Teamsbody = () => {
  const [loading, setLoading] = useState(true);
  const [tasks, setTasks] = useState([]);
  const [groupe, setGroupe] = useState([]);
  const [userId, setUserId] = useState(null);


  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const obtainedUserId = await UserService.getIdByUsername();
  
          // Set the obtained user ID to the state
        setUserId(obtainedUserId);
        const response = await GroupeService.getGroupByid();
        setTasks(response.data);
        setLoading(false);
      } catch (error) {
        console.log(error);
      }
    };
    fetchData();
    fetchGroupe();
  }, []);

  const fetchGroupe = async () =>{
    setLoading(true);
    try {
      const responsegroupe = await GroupeService.getGroupforuser(userId);
      setGroupe(responsegroupe.data);
      setLoading(false);

    } catch(error){
      console.log(error);

    }
  };

  
  const bodyStyle = {
    fontFamily: "Helvetica Neue, Helvetica, Arial, sans-serif",
    fontSize: "13px",
    color: "#555",
    background: "none",
    marginTop: "100px",
  };

  const noTasksStyle = {
    textAlign: "center",
    fontSize: "18px",
    color: "#888",
    marginTop: "20px",
  };
  
  const columnButton = {
    background: 'none',
    color: 'inherit',
    border: 'none',
    padding: 0,
    fontFamily: 'inherit', // Adjust this line based on your specific use case
    cursor: 'pointer',
    outline: 'inherit',
    fontWeight: 'bold'
  };

  return (
    <div style={bodyStyle}>
      <h1 className="text-center text-dark mb-5 font-weight-bold">My Teams</h1>
      <div className="container bootstrap snippets bootdey">
        <div className="table-responsive">
            <div className="d-flex" style={{maxWidth : '400px'}}>
              
              {tasks.map((task) => (
              <Link key={task.id} to={`/teams/${task.id}`} style={{ textDecoration: 'none' }}>
                <div
                  className="card bg-light mx-3"
                  style={{ height: '300px', width: '300px', justifyContent: 'center', alignItems: 'center', fontSize: '30px' }}
                >
                  
                    {task.name}
                    <span style={{ fontSize: '10px' }}>owned by <strong>{task.owner.username}</strong></span>
                  
                </div>
              </Link>
            ))}
            {groupe.map((groupes) => (
              <Link key={groupes.id} to={`/teams/${groupes.id}`} style={{ textDecoration: 'none' }}>
                <div
                  className="card bg-light mx-3"
                  style={{ height: '300px', width: '300px', justifyContent: 'center', alignItems: 'center', fontSize: '30px' }}
                >
                  
                    {groupes.name}
                    <span style={{ fontSize: '10px' }}>owned by <strong>{groupes.owner.username}</strong></span>
                  
                </div>
              </Link>
            ))}
            </div>
            

        </div>
      </div>
    </div>
  );
};

export default Teamsbody;
