package gs.teamup.bot.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import gs.teamup.bot.jdbc.client.dao.ClientDAO;
import gs.teamup.bot.jdbc.client.model.Client;
import gs.teamup.bot.jdbc.customer.dao.CustomerDAO;
import gs.teamup.bot.jdbc.itcuser.model.ItcUser;
import gs.teamup.bot.pojo.edge.Button;
import gs.teamup.bot.pojo.edge.ChatMessage;
import gs.teamup.bot.pojo.edge.ExtraV2;
import gs.teamup.bot.pojo.event.TeamupEventChat;
import gs.teamup.bot.template.teamup.EdgeTemplate;
import gs.teamup.bot.jdbc.common.StringUtil;

@Component
@EnableScheduling
public class ChatService {
	private static final Log log = LogFactory.getLog(ChatService.class);
	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Module-Client.xml");
	ApplicationContext context1 = new ClassPathXmlApplicationContext("Spring-Module.xml");
	ClientDAO clientDAO = (ClientDAO) this.context.getBean("clientDAO");
	CustomerDAO customerDAO = (CustomerDAO) this.context1.getBean("customerDAO");
	@Autowired
	private EdgeTemplate edgeTemplate;
	@Value("${project.version}")
	private String version;
	private String infoCmt = "안녕하세요.\n원내전화번호부(시설팀) 조회 서비스를 시범적으로 제공하고 있습니다.\n\n"
			+ "(명칭 검색 예시) 초음파\n"
			+ "(명칭 검색 예시) 나5\n"
			+ "(명칭 검색 예시) 신경  접수\n"
	        + "(번호 검색 예시) 2379";

    public void doWelcome(TeamupEventChat eventChat) { // 대화방 입장 시 실행
        this.edgeTemplate.say(eventChat.getRoom(), this.infoCmt);
    }
	
	public void doChat(ChatMessage chatMessage, int room) throws UnsupportedEncodingException {
		String content = chatMessage.getContent().trim();
		if ((content.equals("/?")) || (content.equals("/help")) || (content.equals("/도움말")) || (content.equals("?"))
				|| (content.equals("help")) || (content.equals("도움말"))) {
			this.edgeTemplate.say(room, this.infoCmt);
		} else if (content.equals("ver")) {
			this.edgeTemplate.say(room, this.version);
		}
		
		else if (StringUtil.isNumeric(content)) {
			StringBuffer msg = new StringBuffer();
			List<String> plist = this.customerDAO.getPhoneName(content);
			
			if (plist.size() == 0) {
	        	this.edgeTemplate.say(room, "검색결과가 없습니다. 검색어를 다시 입력해 주세요.\n(명칭 검색 예시) 초음파\n(명칭 검색 예시) 나5\n(명칭 검색 예시) 신경  접수\n(번호 검색 예시) 2379");
	        } else {
	        	for(Object obj : plist) {
	        		msg.append((String)obj+"\n");
	        	}
	        	this.edgeTemplate.say(room, msg.toString());
	        }
		}

		else {
			StringBuffer msg = new StringBuffer();
			List<String> plist = this.customerDAO.getPhoneNumber(content);
			
			if (plist.size() == 0) {
	        	this.edgeTemplate.say(room, "검색결과가 없습니다. 검색어를 다시 입력해 주세요.\n(명칭 검색 예시) 초음파\n(명칭 검색 예시) 나5\n(명칭 검색 예시) 신경  접수\n(번호 검색 예시) 2379");
	        } else {
	        	for(Object obj : plist) {
	        		msg.append((String)obj+"\n");
	        	}
	        	this.edgeTemplate.say(room, msg.toString());
	        }
		}

/*		else if (content.startsWith("/진료일정"))
	    {
	      if ((content.equals("/진료일정")) || (content.equals("/진료일정/")))
	      {
	        this.edgeTemplate.say(room, this.infoCmt);
	      }
	      else
	      {
	    	  String msg=this.customerDAO.getDeptDoctorCode(content);
	        
	        String infoMsg = "==요청하신 부서의 진료일정입니다.==\n\n";
	        
	       
	        if (msg.equals("[]")) {
	          this.edgeTemplate.say(room, "검색결과가 없습니다. 검색어를 다시 입력해 주세요.");
	        } else {
	        String deptcode="https://www.cmcseoul.or.kr/page/department/A/";
	          this.edgeTemplate.say(room, infoMsg +deptcode+msg+"/3");
	        }
	      }
	    }
	    
	    else if (content.startsWith("/진료가능의사"))
	    {
	      if ((content.equals("/진료가능의사")) || (content.equals("/진료가능의사/")))
	      {
	        this.edgeTemplate.say(room, this.infoCmt);
	      }
	      else
	      {
	    	  String msg=this.customerDAO.getDeptDoctorCode(content);
	        
	        String infoMsg = "==요청하신 부서의 진료가능의사입니다.==\n\n";
	        
	       
	        if (msg.equals("[]")) {
	          this.edgeTemplate.say(room, "검색결과가 없습니다. 검색어를 다시 입력해 주세요.");
	        } else {
	        String deptcode="https://www.cmcseoul.or.kr/page/department/A/";
	          this.edgeTemplate.say(room, infoMsg +deptcode+msg+"/2");
	        }
	      }
	    }
	    
		else if (content.startsWith("/포탈")) {
			if ((content.equals("/포탈")) || (content.equals("/포탈/"))) {
				this.edgeTemplate.say(room, this.infoCmt);
			} else {
				String msg = this.customerDAO.getSvrHealth(content);

				String infoMsg = "==포탈체크 ==\n\n";

				if (msg.equals("[]")) {
					this.edgeTemplate.say(room, "응답이 없습니다. AP상태를 확인해주세요.");
				} else {
					String deptcode = "서버상태를 확인중입니다. \n\n";
					this.edgeTemplate.say(room, infoMsg + deptcode + msg);
				}
			}
		}

		else if (content.startsWith("/부서")) {
			if ((content.equals("/부서")) || (content.equals("/부서/"))) {
				this.edgeTemplate.say(room, this.infoCmt);
			} else {
				List<Client> clients = this.clientDAO.findTelbyDeptname(content);

				String infoMsg = "==요청하신 부서번호입니다.==\n\n";

				String clmsg = clients.toString().substring(1).replaceFirst("]", "").replace(", ", "");
				if (clmsg.equals("[]")) {
					this.edgeTemplate.say(room, "검색결과가 없습니다. 검색어를 다시 입력해 주세요.");
				} else {
					this.edgeTemplate.say(room, infoMsg + clmsg);
				}
			}
		} else if (content.startsWith("/안내")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String HosStr = UserValues[2];
			System.out.println(UserValues[2]);
			if ((content.equals("/안내")) || (content.equals("/안내/"))) {
				this.edgeTemplate.say(room, this.infoCmt);
			} else {
				String infoMsg = "==요청하신 병원안내입니다.==\n\n";
				String baselink = null;
				if (HosStr.startsWith("서울")) {
					baselink = "https://m.cmcseoul.or.kr/";
				} else if (HosStr.startsWith("여의도")) {
					baselink = "https://m.cmcsungmo.or.kr/";
				} else if (HosStr.startsWith("의정부")) {
					baselink = "https://m.cmcujb.or.kr/";
				} else if (HosStr.startsWith("빈센트")) {
					baselink = "https://m.cmcvincent.or.kr/";
				} else if (HosStr.startsWith("바오로")) {
					baselink = "https://m.cmcbaoro.or.kr/";
				} else if (HosStr.startsWith("부천")) {
					baselink = "https://m.cmcbucheon.or.kr/";
				}
				String infoLink = baselink + "hospitalguide.map.sp";
				this.edgeTemplate.say(room, infoMsg + infoLink);
			}
		} else if (content.startsWith("/itc")) {
			if ((content.equals("/itc")) || (content.equals("/itc/"))) {
				this.edgeTemplate.say(room, this.infoCmt);
			} else {
				List<ItcUser> itu = this.clientDAO.findUserbyPost(content);
				String infoMsg = "==요청하신 대표ITC 정보입니다.==\n\n";

				String clmsg = itu.toString().substring(1).replaceFirst("]", "").replace(", ", "");
				if (clmsg.equals("[]")) {
					this.edgeTemplate.say(room, "검색결과가 없습니다. 검색어를 다시 입력해 주세요.");
				} else {
					this.edgeTemplate.say(room, infoMsg + clmsg);
				}
			}
		} else if (content.startsWith("/위치")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String DeptStr = UserValues[2];
			System.out.println(UserValues[2]);
			if ((content.equals("/위치")) || (content.equals("/위치/"))) {
				this.edgeTemplate.say(room, this.infoCmt);
			} else {
				String infoMsg = "==요청하신 과위치입니다.==\n\n";

				String infoLink = "https://m.cmcseoul.or.kr/page/hospitalguide/lookaround?placeNm=" + DeptStr;
				this.edgeTemplate.say(room, infoMsg + infoLink);
			}
		} else if (content.startsWith("/사번")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String Pid = UserValues[2];
			System.out.println(UserValues[2]);
			try {
				String url = "http://erp001.cmcnu.or.kr/cmcnu/webapps/mi/rp_humtrafactmngtweb/.live?submit_id=DRRPB00101&ep_interface=&business_id=mi&emplno="
						+ Pid + "&instcd=012&cbflag=Y";

				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				NodeList nList = doc.getElementsByTagName("empllist");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {
						String viewfairnmdd = "";
						String viewbaptnm = "";
						Element eElement = (Element) nNode;
						String tmpfairnmdd = getTagValue("fairnmdd", eElement);
						String tmpbaptnm = getTagValue("baptnm", eElement);
						if ((tmpfairnmdd == "-") || (tmpbaptnm == "-")) {
							viewbaptnm = "-";
							viewfairnmdd = "-";
						} else {
							viewfairnmdd = tmpfairnmdd.substring(0, 2) + "월" + tmpfairnmdd.substring(2, 4) + "일";
							viewbaptnm = tmpbaptnm;
						}
						this.edgeTemplate.say(room, "요청하신 사용자 정보입니다.\n######################\n이름:" +

								getTagValue("name", eElement) + '\n' + "사번:" + getTagValue("emplno", eElement) + '\n'
								+ "부서:" + getTagValue("unitnm", eElement) + '\n' + "내선번호:"
								+ getTagValue("hosinseqno", eElement) + '\n' + "핸드폰:"
								+ getTagValue("mpphonno", eElement) + '\n' + "세례명:" + viewbaptnm + '\n' + "축일:"
								+ viewfairnmdd + '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (content.startsWith("/직원")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String usernm = UserValues[2];
			System.out.println(UserValues[2]);
			try {
				// String url =
				// "http://erp001.cmcnu.or.kr/cmcnu/webapps/mi/rp_humtrafactmngtweb/.live?submit_id=DRRPB00101&ep_interface=&business_id=mi&emplno=&instcd=012&cbflag=Y&name="
				// + usernm;
				// 20190208
				String url = "http://erp001.cmcnu.or.kr/cmcnu/webapps/mi/rp_humtrafactmngtweb/.live?submit_id=DRRPB00166&ep_interface=&business_id=mi&emplno=&instcd=012&cbflag=Y&name="
						+ usernm;
				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				NodeList nList = doc.getElementsByTagName("empllist");
				System.out.println("파싱할 리스트 수 : " + nList.getLength());
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {
						String viewfairnmdd = "";
						String viewbaptnm = "";
						Element eElement = (Element) nNode;
						String tmpfairnmdd = getTagValue("fairnmdd", eElement);
						String tmpbaptnm = getTagValue("baptnm", eElement);
						if ((tmpfairnmdd == "-") || (tmpbaptnm == "-")) {
							viewbaptnm = "-";
							viewfairnmdd = "-";
						} else {
							viewfairnmdd = tmpfairnmdd.substring(0, 2) + "월" + tmpfairnmdd.substring(2, 4) + "일";
							viewbaptnm = tmpbaptnm;
						}
						this.edgeTemplate.say(room, "요청하신 사용자 정보입니다.\n######################\n이름:" +

								getTagValue("name", eElement) + '\n' + "사번:" + getTagValue("emplno", eElement) + '\n'
								+ "부서:" + getTagValue("unitnm", eElement) + '\n' + "내선번호:"
								+ getTagValue("hosinseqno", eElement) + '\n' + "핸드폰:"
								+ getTagValue("mpphonno", eElement) + '\n' + "세례명:" + viewbaptnm + '\n' + "축일:"
								+ viewfairnmdd + '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (content.startsWith("/휴가")) {
			String tmupidx = "/휴가/" + chatMessage.getUser().toString();
			String userid = this.clientDAO.findPidByTmupidx(tmupidx);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

			Calendar c1 = Calendar.getInstance();

			String strToday = sdf.format(c1.getTime());
			try {
				String url = "http://erp001.cmcnu.or.kr/cmcnu/webapps/mi/rp_dligclaznsmngtweb/.live?submit_id=DRRPD30001&ep_interface=&business_id=mi&emplno="
						+ userid + "&instcd=012&dutym=" + strToday;

				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				NodeList nList = doc.getElementsByTagName("offcntlist");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {
						Element eElement = (Element) nNode;
						System.out.println("######################");
						this.edgeTemplate.say(room, "요청하신 휴가 정보입니다.\n######################\n발생연차:" +

								getTagValue("genryearno", eElement) + '\n' + "사용연차:"
								+ getTagValue("useyearno", eElement) + '\n' + "가용연차:"
								+ getTagValue("spreyearno", eElement) + "일 입니다. " + '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (content.startsWith("/당직")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String deptNm = UserValues[2];
			System.out.println(UserValues[2]);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar c1 = Calendar.getInstance();
			String strToday = sdf.format(c1.getTime());
			try {
				String url = "http://emr012.cmcnu.or.kr/cmcnu/.live?submit_id=DRMMO30009&business_id=mr&instcd=012&workdd="
						+ strToday + "&orddeptnm=" + deptNm;

				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				System.out.println("url :" + url);

				NodeList nList = doc.getElementsByTagName("chatbotworkdrinfo");
				System.out.println("파싱할 리스트 수 : " + nList.getLength());
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {
						Element eElement = (Element) nNode;
						System.out.println("######################");
						this.edgeTemplate.say(room,
								"요청하신 당직 정보입니다.\n######################\n파트구분:" + getTagValue("partcdnm", eElement)
										+ '\n' + "시작종료시간:" + getTagValue("partfromtotm", eElement) + '\n' + "의사1:"
										+ getTagValue("drnm1", eElement) + '\n' + "의사2:"
										+ getTagValue("drnm2", eElement) + '\n' + "의사3:"
										+ getTagValue("drnm3", eElement) + '\n' + "스텝:" + getTagValue("drnm4", eElement)
										+ '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (content.startsWith("/예약현황")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String Pid = UserValues[2];
			System.out.println(UserValues[2]);
			try {
				String url = "http://emr012.cmcnu.or.kr/cmcnu/.live?submit_id=DRPMH00002&business_id=pm&instcd=012&pid="
						+ Pid;

				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

				NodeList nList = doc.getElementsByTagName("mychart_rsrv_o");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {

						Element eElement = (Element) nNode;

						this.edgeTemplate.say(room, "요청하신 예약현황입니다.\n######################\n구분:" +

								getTagValue("gubun", eElement) + '\n' + "예약일:" + getTagValue("rsrvdt", eElement) + '\n'
								+ "부서:" + getTagValue("orddeptnm", eElement) + '\n' + "의료진:"
								+ getTagValue("orddrnm", eElement) + '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// pacs
		else if ((content.startsWith("/팍스")) || (content.startsWith("/pacs")) || (content.startsWith("/PACS"))) {
			pacsCheck(room, content);
		}

		else if (content.startsWith("/병실")) {
			String[] UserValues = content.split("/");
			String instStr = UserValues[1];
			System.out.println(UserValues[1]);
			String userNm = UserValues[2];
			System.out.println(UserValues[2]);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar c1 = Calendar.getInstance();
			String strToday = sdf.format(c1.getTime());
			//http://emr012.cmcnu.or.kr/cmcnu/.live?submit_id=DRPMC02100&&ex_interface=MOB%7C012&business_id=pm&srchcondflag=1&instcd=012&srchcond
			try {
				String url = "http://emr012.cmcnu.or.kr/cmcnu/.live?submit_id=DRPMC02100&&ex_interface=MOB%7C012&business_id=pm&srchcondflag=1&instcd=012&srchcond="
						+ userNm;
				
				// String url = "http://emr012.cmcnu.or.kr/cmcnu/.live?submit_id=DRPMC02100&ep_interface=MOB|012&business_id=pm&srchcondflag=1&instcd=012&srchcond="
				
				

				DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactoty.newDocumentBuilder();
				Document doc = dBuilder.parse(url);

				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				System.out.println("url :" + url);

				NodeList nList = doc.getElementsByTagName("inhosppatlist");
				System.out.println("파싱할 리스트 수 : " + nList.getLength());
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == 1) {
						Element eElement = (Element) nNode;
						System.out.println("######################");
						this.edgeTemplate.say(room,
								"요청하신 병실 정보입니다.\n######################\n이름:" + getTagValue("hngnm", eElement) + '\n'
										+ "성별:" + getTagValue("sexnm", eElement) + '\n' + "나이:"
										+ getTagValue("age", eElement) + '\n' + "진료과:"
										+ getTagValue("orddeptnm", eElement) + '\n' + "병실:"
										+ getTagValue("roomnm", eElement) + '\n' + '\n');
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
*/
	}

	private void pacsCheck(int room, String content) {
		String pacsrltmsg = getPacsSvrHealth(content);
		this.edgeTemplate.say(room, pacsrltmsg);
	}

	public String getPacsSvrHealth(String content) {
		String[] UserValues = content.split("/");
		String instStr = UserValues[1];
		System.out.println(UserValues[1]);
		String HosStr = UserValues[2];
		System.out.println(UserValues[2]);

		String baselink = null;
		if (HosStr.startsWith("서울")) {
			baselink = "http://m012.cmcnu.or.kr:8080/xwado/wadoserver.svc/";

			// baselink =
			// "http://m012.cmcnu.or.kr:8080/xWADO20/PushWADO?requestType=WADO&contentType=image/jpeg&studyUID=1.2.410.200003.1037.1.0.14802403.20180306.141500.1806406419.1&seriesUID=1.3.46.670589.26.200078.2.20180306.142815.117490&&objectUID=1.3.46.670589.26.200078.4.20180306.144126.117490.0&dicom=\\160.1.73.35\LTN07\20180306\CR\14802403_20180306_1806406419_CR\14802403_20180306_142815_117490_00000_CR.dcm";
		} else if (HosStr.startsWith("여의도")) {
			baselink = "http://m011.cmcnu.or.kr:8080/xwadodata/wadoserver.svc/";
		} else if (HosStr.startsWith("의정부")) {
			baselink = "http://m013.cmcnu.or.kr:8080/xwadodata/wadoserver.svc/";
		} else if (HosStr.startsWith("빈센트")) {
			baselink = "http://m017.cmcnu.or.kr:8080/xwadodata/wadoserver.svc/";
		} else if (HosStr.startsWith("바오로")) {
			baselink = "http://m015.cmcnu.or.kr:8080/xwadodata/wadoserver.svc/";
		}

		String pacsrltmsg = "";

		try {
			// URL url = new
			// URL("http://emr015.cmcnu.or.kr/cmcnu/.live?submit_id=DRMOB01001&business_id=mb&ex_interface=MOB%7C015&instcd=015&imgetype=IMG&imgekey=104943525");
			URL url = new URL(baselink);
			InputStream openStream = url.openStream();
			InputStreamReader isr1 = new InputStreamReader(openStream, "UTF-8");
			BufferedReader bis1 = new BufferedReader(isr1);
			System.out.println("-------------------------------------------------------");
			System.out.println("페이지정보 : ");
			while (true) {
				String str = bis1.readLine(); // 한줄을 읽어서
				if (str == null) {
					break;
				}
				System.out.println(str);

				if (str.contains("xWADO")) {
					pacsrltmsg = HosStr + " PACS 서버가 정상동작중입니다." + '\n' + str;
				} else {
					pacsrltmsg = "서버상태를 확인해 주세요";
				}

			}

			bis1.close();
			isr1.close();

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			pacsrltmsg = "서버상태를 확인해 주세요";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			pacsrltmsg = "서버상태를 확인해 주세요";
		}
		return pacsrltmsg;
	}

	public String getPacsSvrHealthAll() {
		String svresult = "";
		String result = "";
		String msg011 = this.getPacsSvrHealth("/PACS/여의도");
		String msg012 = this.getPacsSvrHealth("/PACS/서울");
		String msg013 = this.getPacsSvrHealth("/PACS/의정부");
		String msg015 = this.getPacsSvrHealth("/PACS/바오로");
		String msg017 = this.getPacsSvrHealth("/PACS/빈센트");

		if (!(msg011.contains("정상동작"))) {
			svresult = svresult + "여의도,";
		}
		if (!(msg012.contains("정상동작"))) {
			svresult = svresult + "서울,";
		}
		if (!(msg013.contains("정상동작"))) {
			svresult = svresult + "의정부,";
		}
		if (!(msg015.contains("정상동작"))) {
			svresult = svresult + "바오로,";
		}
		if (!(msg017.contains("정상동작"))) {
			svresult = svresult + "빈센트";
			System.out.println("svr 017 : " + svresult);

		}

		if (svresult.contentEquals("")) {
			System.out.println("svr cont : " + svresult);
			result = "모든 PACS 서버가 정상동작중입니다.";
		} else {
			result = svresult + " PACS 서버를 확인해주십시요";
		}

		return result;

	}

	private static String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = nlList.item(0);
		if (nValue == null) {
			return "-";
		}
		return nValue.getNodeValue();
	}
}
