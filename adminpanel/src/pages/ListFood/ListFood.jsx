import React,{useState,useEffect} from 'react';
import axios from 'axios';
import { getProductImageUrl } from '../../services/imageService';
import './ListFood.css';
import { toast } from 'react-toastify';
import {deleteFood, getFoodList} from '../../services/foodService';

const ListFood = () => {
  
  
  const [list,setList] = useState([]);
  const fetchList = async() => {
    try {
      const data = await getFoodList();
      setList(data);
    } catch (error) {
      toast.error('Error while reading the foods');
    }
  }



  const removeFood = async (foodId) => {
    
      try{
        
        const isFoodDeleted = await deleteFood(foodId);
        if(isFoodDeleted){
          toast.success('Food removed');
          await fetchList();
        }
        else{
          toast.error('Error occurred while removing the food');
        }
        
      }catch(error){
         toast.error('Error occurred while removing the food');
      }
  }

  useEffect(() => {
    fetchList();
  },[]);
  return (
     <div className="py-5 row justify-content-center">
       <div className="col-11 card">
        <table className="table">
          <thead>
            <tr>
              <th>Image</th>
              <th>Name</th>
              <th>Category</th>
              <th>Price</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {
              list.map((item,index) => {
                return(
                  <tr key={index}>
                    <td>
                      <img src={getProductImageUrl(item.imageName)} alt="" height={48} width={48}></img>
                    </td>
                    <td>{item.name}</td>
                    <td>{item.category}</td>
                    <td>&#8377;{item.price}.00</td>
                    <td className='text-danger'>
                      <i className='bi bi-x-circle-fill' onClick={() => removeFood(item.id)}></i>
                    </td>
                  </tr>
                )
              })
            }
          </tbody>
        </table>
       </div>
     </div>
  )
}

export default ListFood;