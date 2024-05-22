package dbapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import e4i.domain.ResearchInstitution;
import e4i.domain.Researcher;
import e4i.util.LatCyrUtils;
import e4i.util.Utility;
import io.swagger.client.model.Company__1;
import io.swagger.client.model.Zaposlenja;
import io.swagger.client.model.Zvanja;


public class DBManage {
	
	
	List<ResearchInstitution> updatelist;
	List<ResearchInstitution> insertlist;
	
	List<Researcher> updatelistRE;
	List<Researcher> insertlistRE;
	
	List<io.swagger.client.model.Researcher> updatelistRERI;
	List<io.swagger.client.model.Researcher> insertlistRERI;
	
	List<Long> deleteFORRERI;
	List<Company__1> insertCOM;

	private final String url = "jdbc:postgresql://localhost:5434/riportal";
	private final String user = "riportal";
	private final String password = "riportal";
	
	
	private static final String INSERT_COM_SQL = "INSERT INTO portal_user_organization" +
	        "  (legal_name_en, legal_name_sr, legal_name_sr_cyr, pib, registration_number) VALUES " +
	        " (?, ?, ?, ?, ?)";
	
	private static final String DELETE_COM_SQL = "DELETE FROM  portal_user_organization";
	
	private static final String SELECT_ALL_RI_QUERY = "select * from research_institution";
	private static final String INSERT_RI_SQL = "INSERT INTO research_institution" +
	        "  (id, name, address, ceo_ibi, ceo_name,ceo_last_name,name_en,number, owner_structure_name,register_number,state"
	        + ", type_name, vat_number, web_adress, name_cyr) VALUES " +
	        " (?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?, ?,?, ?);";
	private static final String UPDATE_RI_SQL = "update research_institution set " +
	        "  name=?, address=?, ceo_ibi=?, ceo_name=?,ceo_last_name=?,name_en=?,number=?, owner_structure_name=?,register_number=?,state=?,"
	        + " type_name=?, vat_number=?, web_adress=?, name_cyr=? where id=?;";
	
	
	private static final String SELECT_ALL_RE_QUERY = "select * from researcher";
	private static final String SELECT_ALL_RE_RI_QUERY = "select * from research_institution_researchers";
	
	private static final String INSERT_RE_SQL = "INSERT INTO researcher" +
	        "  (id, birth_date, first_name,last_name, middle_name, first_name_lat,last_name_lat, middle_name_lat) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String INSERT_RE_RI_SQL = "INSERT INTO research_institution_researchers" +
	        "  (research_institution_id, researchers_id) VALUES (?, ?);";
		
	private static final String UPDATE_RE_SQL = "update researcher set birth_date=?, first_name=?, last_name=?, middle_name=?, first_name_lat=?, last_name_lat=?, middle_name_lat=? where id=?;";
	private static final String UPDATE_RE_RI_SQL = "update research_institution_researchers set research_institution_id=? where researchers_id=?;";
	
	
	private String DELETE_RE_RI = "delete from research_institution_researchers where research_institution_researchers.researchers_id in ";

	private static final String COPY_RI_TO_PUO_SQL = "INSERT INTO portal_user_organization (legal_name_sr,legal_name_en,legal_name_sr_cyr, address, registration_number,pib, web_address, nio_institution_id) "
			+ "	SELECT name,name_en,name_cyr, address, register_number, vat_number,web_adress, id FROM research_institution ORDER BY research_institution.id ";
	
	
	private static final String COPY_RI_TO_RIRORG_BY_PUO_SQL = "INSERT INTO ri_research_organization (name, pu_organization_id) "
			+ "	SELECT portal_user_organization.legal_name_sr, portal_user_organization.id FROM portal_user_organization INNER JOIN research_institution ON portal_user_organization.nio_institution_id = research_institution.id "
			+ " ORDER BY portal_user_organization.id;";
	
	
	private static final String DELETE_PUO_RI_SQL = "DELETE FROM portal_user_organization USING research_institution WHERE portal_user_organization.nio_institution_id = research_institution.id";
	private static final String DELETE_RIRIORG_SQL = "DELETE FROM ri_research_organization USING portal_user_organization " 
			+ "WHERE portal_user_organization.id = ri_research_organization.pu_organization_id "
			+ "AND portal_user_organization.nio_institution_id > 0;";
	
	
	
	public void copyRItoPUO() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(COPY_RI_TO_PUO_SQL);) {
	           
	            int result = statement.executeUpdate();
	        
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	public void delete_RItoPUO() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(DELETE_PUO_RI_SQL);) {
	           
	            int result = statement.executeUpdate();
	        
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	public void copyPUORItoTRIORG() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(COPY_RI_TO_RIRORG_BY_PUO_SQL);) {
	           
	            int result = statement.executeUpdate();
	        
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	
	public void delete_PUORItoTRIORG() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(DELETE_RIRIORG_SQL);) {
	           
	            int result = statement.executeUpdate();
	        
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	public void insertCompanies() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(INSERT_COM_SQL);) {
	            int count = 0;

	            for (Company__1 com: this.insertCOM) {
	               // statement.setLong(1, re.getId().longValue());
	                
	                statement.setString(1, com.getName());
	                statement.setString(2, com.getName());
	                statement.setString(3, LatCyrUtils.toCyrillic(com.getName()));
	                statement.setString(4, com.getTaxNo());
	                statement.setString(5, com.getRegNo());
	                
	                statement.addBatch();
	                count++;
	                // execute every 100 rows or less
	                if (count % 100 == 0 || count == this.insertCOM.size()) {
	                    statement.executeBatch();
	                }
	            }
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	public void deleteCompanies() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(DELETE_COM_SQL);) {
	            int count = 0;

	            int result = statement.executeUpdate();
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	public void insertRecordResearcher() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(INSERT_RE_SQL);) {
	            int count = 0;

	            for (Researcher re: this.insertlistRE) {
	                statement.setLong(1, re.getId().longValue());
	                
	                java.sql.Date date = java.sql.Date.valueOf(re.getBirthDate());
	                statement.setDate(2, date);
	              //  statement.setString(3, re.getFirstName());
	              //  statement.setString(4, re.getLastName());
	              //  statement.setString(5, re.getMiddleName());
	               
	               
	                if(re.getFirstName() != null && !"".equals(re.getFirstName())) {
	            		if(Utility.containCyrillic(re.getFirstName())) {
	            			statement.setString(3, re.getFirstName());
	    	                statement.setString(4, re.getLastName());
	    	                statement.setString(5, re.getMiddleName());
	    	                statement.setString(6, LatCyrUtils.toLatin(re.getFirstName()));
	    	                statement.setString(7, LatCyrUtils.toLatin(re.getLastName()));
	    	                statement.setString(8, LatCyrUtils.toLatin(re.getMiddleName()));
	            			
	            		}else {
	            			//LatCyrUtils.toLatin(nameCyr);
	            			statement.setString(3, LatCyrUtils.toCyrillic(re.getFirstName()));
	    	                statement.setString(4, LatCyrUtils.toCyrillic(re.getLastName()));
	    	                statement.setString(5, LatCyrUtils.toCyrillic(re.getMiddleName()));
	    	                statement.setString(6, re.getFirstName());
	    	                statement.setString(7, re.getLastName());
	    	                statement.setString(8, re.getMiddleName());
	            		}			
	        		}	   
	                
	                
	                statement.addBatch();
	                count++;
	                // execute every 100 rows or less
	                if (count % 100 == 0 || count == this.insertlistRE.size()) {
	                    statement.executeBatch();
	                }
	            }
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	public void updateRecordResearcher() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(UPDATE_RE_SQL);) {
	            int count = 0;

	            for (Researcher re: this.updatelistRE) {
	                //statement.setLong(1, re.getId().longValue());
	                java.sql.Date date = java.sql.Date.valueOf(re.getBirthDate());
	                statement.setDate(1, date);
	              //  statement.setString(2, re.getFirstName());
	              //  statement.setString(3, re.getLastName());
	              //  statement.setString(4, re.getMiddleName());
	                
	                if(re.getFirstName() != null && !"".equals(re.getFirstName())) {
	            		if(Utility.containCyrillic(re.getFirstName())) {
	            			statement.setString(2, re.getFirstName());
	    	                statement.setString(3, re.getLastName());
	    	                statement.setString(4, re.getMiddleName());
	    	                statement.setString(5, LatCyrUtils.toLatin(re.getFirstName()));
	    	                statement.setString(6, LatCyrUtils.toLatin(re.getLastName()));
	    	                statement.setString(7, LatCyrUtils.toLatin(re.getMiddleName()));
	            		}else {
	            			//LatCyrUtils.toLatin(nameCyr);
	            			statement.setString(2, LatCyrUtils.toCyrillic(re.getFirstName()));
	    	                statement.setString(3, LatCyrUtils.toCyrillic(re.getLastName()));
	    	                statement.setString(4, LatCyrUtils.toCyrillic(re.getMiddleName()));
	    	                statement.setString(5, re.getFirstName());
	    	                statement.setString(6, re.getLastName());
	    	                statement.setString(7, re.getMiddleName());
	            		}			
	        		}	  
	                
	                
	                statement.setLong(8, re.getId());
	               
	                statement.addBatch();
	                count++;
	                // execute every 100 rows or less
	                if (count % 100 == 0 || count == this.insertlistRE.size()) {
	                    statement.executeBatch();
	                }
	            }
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	
	
	
	public void insertRecordResearcherInstitution() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(INSERT_RE_RI_SQL);) {
	            int count = 0;
	            //this.checkListRERI();
	            for (io.swagger.client.model.Researcher re: this.insertlistRERI) {
	            	//System.out.println(this.insertlistRERI.size());
	                if (!re.getZaposlenja().isEmpty()) {
	                	
	                	int numZaposlenja = re.getZaposlenja().size();
	                	if (numZaposlenja > 1) {
	                		long reid = re.getId().longValue();
	                		long[] niods = new long[numZaposlenja];
	                		int i = 0;
	                		for(io.swagger.client.model.Zaposlenja zp:re.getZaposlenja()) {
	                			long nioid = zp.getNioID().longValue();
	                			niods[i] = nioid;
	                		    i++;
	                		}
	                		long[] niodsUnique = Arrays.stream(niods).distinct().toArray();
	                		for (int j=0;j<niodsUnique.length;j++) {
	                			statement.setLong(1, niodsUnique[j]);
	                			statement.setLong(2, reid);
	                			statement.addBatch();
	                		}
	                		
	                	} else {
	                		statement.setLong(1, re.getZaposlenja().get(0).getNioID().longValue());
	                		statement.setLong(2, re.getId().longValue()); 
	                		statement.addBatch();
	                	}
	                 
	                } else if (!re.getZvanja().isEmpty()) 
	                {
	                	statement.setLong(1, re.getZvanja().get(0).getNioID().longValue());
	                	statement.setLong(2, re.getId().longValue()); 
                		statement.addBatch();
	                } else {
	                	continue;
	                }
	                count++;
	                // execute every 100 rows or less
	              //  if (count % 100 == 0 || count == this.insertlistRERI.size()) {
	                //    statement.executeBatch();
	                //}
	                
	               // statement.executeBatch();
	            }
	            
	            statement.executeBatch();
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	public void updateRecordResearcherInstitution() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(UPDATE_RE_RI_SQL);) {
	            int count = 0;
	            if (this.updatelistRERI !=null && this.updatelistRERI.size() > 0) {
		            for (io.swagger.client.model.Researcher re: this.updatelistRERI) {
		            	if (!re.getZaposlenja().isEmpty()) {
		                	
		                	int numZaposlenja = re.getZaposlenja().size();
		                	if (numZaposlenja > 1) {
		                		long reid = re.getId().longValue();
		                		long[] niods = new long[numZaposlenja];
		                		int i = 0;
		                		for(io.swagger.client.model.Zaposlenja zp:re.getZaposlenja()) {
		                			long nioid = zp.getNioID().longValue();
		                			niods[i] = nioid;
		                		    i++;
		                		}
		                		long[] niodsUnique = Arrays.stream(niods).distinct().toArray();
		                		for (int j=0;j<niodsUnique.length;j++) {
		                			statement.setLong(1, niodsUnique[j]);
		                			statement.setLong(2, reid);
		                			statement.addBatch();
		                		}
		                		
		                	} else {
		                		statement.setLong(1, re.getZaposlenja().get(0).getNioID().longValue());
		                		statement.setLong(2, re.getId().longValue()); 
		                		statement.addBatch();
		                	}
		                 
		                } else if (!re.getZvanja().isEmpty()) 
		                {
		                	statement.setLong(1, re.getZvanja().get(0).getNioID().longValue());
		                	statement.setLong(2, re.getId().longValue()); 
	                		statement.addBatch();
		                } else {
		                	continue;
		                }
		                count++;
		                // execute every 100 rows or less
		             //   if (count % 100 == 0 || count == this.insertlistRERI.size()) {
		                //    statement.executeBatch();
		              //  }
		            }
		       }
	            statement.executeBatch();
	            
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	
	public List<Researcher> selectAllRE() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
	        try (
	            Connection connection = this.connect();
	        	PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RE_QUERY);) {
	            
	        	ResultSet rs = preparedStatement.executeQuery();
	        	Researcher re;
	        	List<Researcher> ress = new ArrayList<Researcher>();
	        	
	        	 while (rs.next()) {
	        		re = new Researcher();
	        		 
	        		re.setId(rs.getLong("id"));
	        		
	        		Calendar cal = Calendar.getInstance();
	        		cal.setTime(rs.getDate("birth_date"));
	        		LocalDate date = LocalDate.of(cal.get(Calendar.YEAR),
	        		        cal.get(Calendar.MONTH) + 1,
	        		        cal.get(Calendar.DAY_OF_MONTH));
	     			
	     			re.setBirthDate(date);
	     			
	     			re.setFirstName(rs.getString("first_name"));
	     			
	     			re.setLastName(rs.getString("last_name"));
	     			
	     			re.setMiddleName(rs.getString("middle_name"));;
	     			
	     		     			
	     			ress.add(re);
	             }
	        	
	         return ress;
	        	
	            
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	        	 return null;
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	public List<io.swagger.client.model.Researcher> selectAllRERI() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
	        try (
	            Connection connection = this.connect();
	        	PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RE_RI_QUERY);) {
	            
	        	ResultSet rs = preparedStatement.executeQuery();
	        	io.swagger.client.model.Researcher re;
	        	List<io.swagger.client.model.Researcher> ress = new ArrayList<io.swagger.client.model.Researcher>();
	        	
	        	 while (rs.next()) {
	        		 
	        		 
	        		re = new io.swagger.client.model.Researcher();
	        		
	        		re.setId(rs.getInt("researchers_id"));
	        		
	        		Zaposlenja zp = new Zaposlenja(); 
	        		List<Zaposlenja> zps = new ArrayList<>();
	        		zp.setNioID(rs.getInt("research_institution_id"));
	        		zps.add(zp);
	        		re.setZaposlenja(zps);
	        		
	        		Zvanja zv = new Zvanja(); 
	        		List<Zvanja> zvs = new ArrayList<>();
	        		zv.setNioID(rs.getInt("research_institution_id"));
	        		zvs.add(zv);
	        		re.setZvanja(zvs);
	        		
	        	     			
	     			ress.add(re);
	             }
	        	
	         return ress;
	        	
	            
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	        	 return null;
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	
	
	public void insertRecord() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
		
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(INSERT_RI_SQL);) {
	            int count = 0;

	            for (ResearchInstitution ri: this.insertlist) {
	                statement.setLong(1, ri.getId().longValue());
	                
	                statement.setString(3, ri.getAddress());
	                statement.setString(4, ri.getCeoIbi());
	                statement.setString(5, ri.getCeoName());
	                statement.setString(6, ri.getCeoLastName());
	                statement.setString(7, ri.getNameEn());
	                statement.setString(8, ri.getNumber());
	                statement.setString(9, ri.getOwnerStructureName());
	                statement.setString(10, ri.getRegisterNumber());
	                statement.setString(11, ri.getState());
	                statement.setString(12, ri.getTypeName());
	                statement.setString(13, ri.getVatNumber());
	                statement.setString(14, ri.getWebAdress());
	                
	                String nameCyr = null;
	                String name = null;
	                if(ri.getName() != null && !"".equals(ri.getName())) {
	            		if(Utility.containCyrillic(ri.getName())) {
	            			nameCyr = ri.getName();
	            			name = LatCyrUtils.toLatin(nameCyr);
	            		}else {
	            			name = ri.getName();
	            			nameCyr = LatCyrUtils.toCyrillic(name);
	            		}			
	        		}	                
	                
	                statement.setString(2, name);
	                statement.setString(15, nameCyr);
	                statement.addBatch();
	                count++;
	                // execute every 100 rows or less
	                if (count % 100 == 0 || count == this.insertlist.size()) {
	                    statement.executeBatch();
	                }
	            }
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	
	public void updateRecord() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
			
	        try (
	            Connection connection = this.connect();
	            // Step 2:Create a statement using connection object
	        		 PreparedStatement statement = connection.prepareStatement(UPDATE_RI_SQL);) {
	            int count = 0;

	            for (ResearchInstitution ri: this.updatelist) {
	               
	                
	                statement.setString(2, ri.getAddress());
	                statement.setString(3, ri.getCeoIbi());
	                statement.setString(4, ri.getCeoName());
	                statement.setString(5, ri.getCeoLastName());
	                statement.setString(6, ri.getNameEn());
	                statement.setString(7, ri.getNumber());
	                statement.setString(8, ri.getOwnerStructureName());
	                statement.setString(9, ri.getRegisterNumber());
	                statement.setString(10, ri.getState());
	                statement.setString(11, ri.getTypeName());
	                statement.setString(12, ri.getVatNumber());
	                statement.setString(13, ri.getWebAdress());
	                
	                
	                String nameCyr = null;
	                String name = null;
	                if(ri.getName() != null && !"".equals(ri.getName())) {
	            		if(Utility.containCyrillic(ri.getName())) {
	            			nameCyr = ri.getName();
	            			name = LatCyrUtils.toLatin(nameCyr);
	            		}else {
	            			name = ri.getName();
	            			nameCyr = LatCyrUtils.toCyrillic(name);
	            		}			
	        		}	  
	                statement.setString(1, name);
	                statement.setString(14, nameCyr);
	                statement.setLong(15, ri.getId().longValue());
	                
	                statement.addBatch();
	                count++;
	                // execute every 100 rows or less
	                if (count % 100 == 0 || count == this.updatelist.size()) {
	                    statement.executeBatch();
	                }
	            }
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }
	
	
	
	public List<ResearchInstitution> selectAllRI() throws SQLException {
	     //   System.out.println(INSERT_USERS_SQL);
	        // Step 1: Establishing a Connection
	        try (
	            Connection connection = this.connect();
	        	PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RI_QUERY);) {
	            
	        	ResultSet rs = preparedStatement.executeQuery();
	        	ResearchInstitution ris;
	        	List<ResearchInstitution> riss = new ArrayList<ResearchInstitution>();
	        	
	        	 while (rs.next()) {
	        		ris = new ResearchInstitution();
	        		 
	        		ris.setId(rs.getLong("id"));
	     			
	     			ris.setNumber(rs.getString("number"));
	     			
	     			ris.setAddress(rs.getString("address"));
	     			
	     			ris.setRegisterNumber(rs.getString("register_number"));
	     			
	     			ris.setVatNumber(rs.getString("vat_number"));
	     			
	     			ris.setWebAdress(rs.getString("web_adress"));
	     			
	     			ris.setTypeName(rs.getString("type_name"));
	     			
	     			ris.setOwnerStructureName(rs.getString("owner_structure_name"));
	     			
	     			ris.setCeoName(rs.getString("ceo_name"));
	     			
	     			ris.setCeoLastName(rs.getString("ceo_last_name"));
	     			
	     			ris.setCeoIbi(rs.getString("ceo_ibi"));
	     			
	     			ris.setState(rs.getString("state"));
	     			
	     			ris.setName(rs.getString("name"));
	     			
	     			ris.setNameEn(rs.getString("name_en"));
	     			
	     			riss.add(ris);
	             }
	        	
	         return riss;
	        	
	            
	        } catch (SQLException e) {

	        	 System.out.println(e.getMessage());
	        	 return null;
	           
	        }

	        // Step 4: try-with-resource statement will auto close the connection.
	    }

	
	public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
	
	
	
public void checkListsInstitutions(List<ResearchInstitution> apisource,List<ResearchInstitution> dbsource) {
		
		updatelist = new ArrayList<>();
		insertlist = new ArrayList<>();
		
		boolean apiexists;
		
		for (final ResearchInstitution rinstapi : apisource) {
			apiexists = false;
			
			for (final ResearchInstitution rinstdb : dbsource) {
		
				long id = rinstdb.getId();
					if (rinstdb.getId().equals(rinstapi.getId())) {
						updatelist.add(rinstapi);
						apiexists = true;
						break;
					}
			} 
			if (!apiexists)
			insertlist.add(rinstapi);
		
		}
		
	}
	
public void checkListsResearchers(List<Researcher> apisource,List<Researcher> dbsource) {
	
	updatelistRE = new ArrayList<>();
	insertlistRE = new ArrayList<>();
	
	boolean apiexists;
	
	for (final Researcher rinstapi : apisource) {
		apiexists = false;
	//	if (rinstapi.getInstitutions().size() > 1) System.out.println(rinstapi.getInstitutions().toString());
		for (final Researcher rinstdb : dbsource) {
	
			long id = rinstdb.getId();
				if (rinstdb.getId().equals(rinstapi.getId())) {
					updatelistRE.add(rinstapi);
					apiexists = true;
					break;
				}
		} 
		if (!apiexists)
		insertlistRE.add(rinstapi);
	
	}
	
}


public void checkListsResearchersInstitutions(List<io.swagger.client.model.Researcher> apisource,List<io.swagger.client.model.Researcher> dbsource) {
	
	//updatelistRERI = new ArrayList<>();
	insertlistRERI = new ArrayList<>();
	deleteFORRERI = new ArrayList<>();
	
	boolean apiexists;
	
	for (final io.swagger.client.model.Researcher rinstapi : apisource) {
		apiexists = false;
		for (final io.swagger.client.model.Researcher rinstdb : dbsource) {
			   
				if (rinstdb.getId().equals(rinstapi.getId())) {
					deleteFORRERI.add(rinstdb.getId().longValue());
				
				}
		} 
		this.insertlistRERI.add(rinstapi);
	}
	
}

public void deleteFORRERI() throws SQLException {
    //   System.out.println(INSERT_USERS_SQL);
       // Step 1: Establishing a Connection
	   if (deleteFORRERI.size()>0) { 
	    String[] idLong = new String[deleteFORRERI.size()];
	    int l = 0;
		for (final Long iddb : deleteFORRERI) {
			idLong[l] = iddb.toString();
			l++;
		}
 
	   String datas = String.join(",", idLong); // "Java-is-best"
	   this.DELETE_RE_RI = this.DELETE_RE_RI + "(" + datas +");";
	   try (
           Connection connection = this.connect();
    	   PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RE_RI);) {
           
           // Step 3: Execute the query or update query
           int result = preparedStatement.executeUpdate();
           
       } catch (SQLException e) {

       	 System.out.println(e.getMessage());
          
       }
	  }

       // Step 4: try-with-resource statement will auto close the connection.
   }

public boolean checkinlist(List<io.swagger.client.model.Researcher> list,io.swagger.client.model.Researcher element) {
	
	for (final io.swagger.client.model.Researcher researcher : list) {
		
		if (researcher.getId().equals(element.getId()) && researcher.getZaposlenja().get(0).getNioID().equals(element.getZaposlenja().get(0).getNioID())) {
			return true;
		}
		
	}
	
	return false;
}

public List<ResearchInstitution> getInsertlist() {
	return insertlist;
}
public void setInsertlist(List<ResearchInstitution> insertlist) {
	this.insertlist = insertlist;
}
public List<Researcher> getInsertlistRE() {
	return insertlistRE;
}
public void setInsertlistRE(List<Researcher> insertlistRE) {
	this.insertlistRE = insertlistRE;
}
public List<io.swagger.client.model.Researcher> getInsertlistRERI() {
	return insertlistRERI;
}
public void setInsertlistRERI(List<io.swagger.client.model.Researcher> insertlistRERI) {
	this.insertlistRERI = insertlistRERI;
}

public List<Company__1> getInsertCOM() {
	return insertCOM;
}

public void setInsertCOM(List<Company__1> insertCOM) {
	this.insertCOM = insertCOM;
}


	
}
