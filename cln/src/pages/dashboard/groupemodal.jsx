import axios from "axios";
import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import '../../styles/dateTimePicker.css';
import { useNavigate, useParams } from 'react-router-dom';
import GroupeService from '../../services/groupe-service';
import UserService from '../../services/user-service';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import storageService from "../../services/storage-service";

dayjs.extend(utc);

function TeamsModal() {
  const [show, setShow] = useState(false);
  const [addeduser, setAddeduser] = useState('');
  const [usergroupe, setUsergroupe] = useState([]);
  const [usergroupe2, setUsergroupe2] = useState([]);
  const { id } = useParams();
  const navigate = useNavigate();

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  useEffect(() => {
    fetchUsers();

  });

  const checkUserExistence = async () => {
    try {
      // Check if the user exists
      const userExistenceResponse = await UserService.getIdByUsernamenew(addeduser);
      

      if (userExistenceResponse) {
        // If the user exists, invite them to the group
        const inviteResponse = await GroupeService.inviteUserToGroup(id, userExistenceResponse);

        setUsergroupe([...usergroupe, inviteResponse.data]);
      } else {
        console.error('User does not exist.');
        // Handle the case where the user does not exist
      }
    } catch (error) {
      console.error('Error checking or inviting users to the group:', error);
    }
  };


  const fetchUsers = async () => {
    const accessToken = storageService.retrieveAccessToken();
    try {
      const response = await axios.get(`http://localhost:8080/api/groups/users/${id}`, { headers: { Authorization: `Bearer ${accessToken}` } });
      setUsergroupe2(response.data);
      console.log(response.data);
    } catch (error) {
      console.error('Error checking or inviting users to the group:', error);
    } 
      
    
  };

  return (
    <>
      <a onClick={handleShow} style={{ cursor: 'pointer' }} className="w-50 pr-3 pb-4 p-2">
        <div className="card border-0 border-bottom-red shadow-lg shadow-hover ">
          <div className="card-body text-center ">
            <div className="text-center">
              <i className="fa fa-users  fa-4x my-2"></i>
            </div>
            Invite Users
          </div>
        </div>
      </a>

      <Modal
        show={show}
        onHide={handleClose}
        backdrop="static"
        keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title>New Group</Modal.Title>
        </Modal.Header>
        <form onSubmit={checkUserExistence}>
          <Modal.Body>
            <Form.Group className="mb-3" controlId="exampleForm.ControlInput2">
              <Form.Label>User username</Form.Label>
              <Form.Control
                name="username"
                value={addeduser}
                onChange={(e) => setAddeduser(e.target.value)}
                className=' bg-white'
                required
                type="textbox"
              />
            </Form.Group>
            <ul>
              {usergroupe2.map((user) => (
                <li className="text-dark" key={user.id}>{user.username}</li>
              ))}
            </ul>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
            <Button variant="primary" type='submit'>
              Add
            </Button>
            
          </Modal.Footer>
        </form>
      </Modal>
    </>
  );
}

export default TeamsModal;
