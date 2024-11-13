// package events;

// import sim.DonneesSimulation;
// import sim.Robot;

// public class EvenementMessage extends Evenement {
//     private String message ;
//     private Robot robot;
//     private Action action;
//     private DonneesSimulation donneesSimulation;
//     public EvenementMessage(long date, String message) {
//         super(date) ;
//         this.message = message ;
//     }
//     public EvenementMessage(long date, String message, Robot robot, Action action, DonneesSimulation data) {
//         super(date) ;
//         this.message=message ;
//         this.robot=robot ;
//         this.action=action ;
//         this.donneesSimulation = data;
//     }
//     public void execute() {
//         System.out.println("Date: (" + this.getDate() + ") Message:" + this.message) ;
//         if (this.donneesSimulation != null) {
//             switch (action) {
//                 case DEPLACER :
//                         System.out.println("Deplacer "+this.robot.toString()+" to "+this.donneesSimulation.getCarte().getCase(5,5));
//                         this.robot.setPosition(this.donneesSimulation.getCarte().getCase(5,5));
//                         break;
//                 case DEVERSER :
//                     break;
//                 case REMPLIR :
//                     break;
//                 default: System.out.println("Default case");
//             }
//         }
//     }
// }