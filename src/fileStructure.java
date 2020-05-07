public class fileStructure {
    FILE root;
    FILE[] subfile;

    fileStructure(int[] parents,String[] name,String rootname){
        int num =parents.length;
        this.root=new FILE();
        this.subfile=new FILE[num];
        this.root.parent=null;
        this.root.filename=rootname;

        for (int i=0;i<num ;i++){
            subfile[i]=new FILE();
            if(parents[i]==-1){
                subfile[i].parent=this.root;
                subfile[i].filename=root.filename+"\\"+name[i];
            }
            else{
                subfile[i].parent = subfile[parents[i]];
            }
            subfile[i].filename=subfile[i].parent.filename+"\\"+name[i];
        }
    }
}
