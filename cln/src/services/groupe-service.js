import axios from "axios";
import { SERVER_GROUPE_URL, SERVER_URL } from "../shared/constants";
import storageService from "./storage-service";
import { jwtDecode } from "jwt-decode";



class GroupeService {

  async saveGroupe(groupName,ownerId,memberIds) {
    const accessToken = storageService.retrieveAccessToken();
    

    const groupData = {
      groupName: groupName,
      ownerId: ownerId, 
      memberIds: memberIds,
    };

    try {
      const response = await axios.post(`${SERVER_GROUPE_URL}/add?groupName=${groupName}&ownerId=${ownerId}&memberIds=${memberIds}`, groupData, { headers: { Authorization: `Bearer ${accessToken}` } });
      return response.data;
    } catch (error) {
      console.error('Error saving group:', error);
      throw error;
    }
  }

  async inviteUserToGroup(groupId, userId) {
    const accessToken = storageService.retrieveAccessToken();

    const inviteData = {
      groupId: groupId,
      userId: userId,
    };

    try {
      const response = await axios.post(`${SERVER_GROUPE_URL}/invite-user?groupId=${groupId}&userId=${userId}`, inviteData, { headers: { Authorization: `Bearer ${accessToken}` } });
      return response.data;
    } catch (error) {
      console.error('Error inviting user to group:', error);
      throw error;
    }
  }


  getGroupByid(){
   
    const decodeToken = (token) => {
        try {
          const decoded = jwtDecode(token);
          return decoded ? decoded.sub : null; 
        } catch (error) {
          console.error('Error decoding token:', error);
          return null;
        }
      };
      
    const accessToken = storageService.retrieveAccessToken();
    const username = decodeToken(accessToken);
    return axios.get(`${SERVER_GROUPE_URL}/get-groups-by-id/${username}`, { headers: { Authorization: `Bearer ${accessToken}` } });
  }


  getGroupforuser(userId){

  const accessToken = storageService.retrieveAccessToken();
  return axios.get(`${SERVER_GROUPE_URL}/user/group/${userId}`, { headers: { Authorization: `Bearer ${accessToken}` } });

  }

}
export default new GroupeService;