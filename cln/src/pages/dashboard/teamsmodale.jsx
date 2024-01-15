import { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import '../../styles/dateTimePicker.css';
import { useNavigate } from 'react-router-dom';
import GroupeService from '../../services/groupe-service' 
import UserService from '../../services/user-service' 
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';

dayjs.extend(utc);




function TeamsModal() {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const [userId, setUserId] = useState(null);
  const [groupName, setGroupName] = useState('');
  const [ownerId, setOwnerId] = useState('');
  const [members, setMembers] = useState([]);



 

  useEffect(() => {
    async function fetchData() {
      try {
        const obtainedUserId = await UserService.getIdByUsername();

        // Set the obtained user ID to the state
        setUserId(obtainedUserId);
        console.log(userId);
        setMembers(obtainedUserId)
        setOwnerId(obtainedUserId);

        // Update the task state with the new user ID

      } catch (error) {
        console.error('Error fetching data:', error);
        // Handle error as needed
      }
    }

    fetchData();
  }, []);
    // Empty dependency array to run the effect only once on mount

    const handleCreate = (e)=>{
      GroupeService.saveGroupe(groupName , ownerId , members)
      .then((response)=>{
        handleClose();
        console.log(response);
        
      
      }).catch((error)=>{
        console.log(error);
      })
    };

 




  

  return (
    <>
    <a onClick={handleShow} style={{ cursor: 'pointer' }} className="w-50 pr-3 pb-4 p-2">
                    <div className="card border-0 border-bottom-red shadow-lg shadow-hover ">
                      <div className="card-body text-center ">
                        <div className="text-center">    
                          <i className="fa fa-users  fa-4x my-2"></i>
                        </div>
                        Create Team
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
          <Modal.Title>New Groupe</Modal.Title>
        </Modal.Header>
        <form onSubmit={handleCreate}>
        <Modal.Body>
        <Form.Group className="mb-3" controlId="exampleForm.ControlInput2">
        <Form.Label>Groupe name</Form.Label>
        <Form.Control
        name="name"
        value={groupName}
        onChange={(e) => setGroupName(e.target.value)}
        className=' bg-white'  required type="textbox"/>
        
      </Form.Group> 
        </Modal.Body>
        <Modal.Footer>

    <Button variant="secondary" onClick={handleClose}>
        Close
       </Button>
     <Button variant="primary" type='sumbit'>Create</Button>
      
      </Modal.Footer>
</form>  
      </Modal>
    </>
  );
}

export default TeamsModal;