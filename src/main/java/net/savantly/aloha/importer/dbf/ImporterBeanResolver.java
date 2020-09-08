package net.savantly.aloha.importer.dbf;

import java.io.Serializable;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import net.savantly.aloha.importer.domain.adjtime.AdjustedTimeImporter;
import net.savantly.aloha.importer.domain.cat.CatImporter;
import net.savantly.aloha.importer.domain.cmp.CompImporter;
import net.savantly.aloha.importer.domain.emp.EmployeeImporter;
import net.savantly.aloha.importer.domain.gif.GiftCardImporter;
import net.savantly.aloha.importer.domain.gndadjck.GndAdjAckImporter;
import net.savantly.aloha.importer.domain.gnddepst.GndDepstImporter;
import net.savantly.aloha.importer.domain.gnddrwr.GndDrwrImporter;
import net.savantly.aloha.importer.domain.gnditem.GndItemImporter;
import net.savantly.aloha.importer.domain.gndlbsum.GndLbSumImporter;
import net.savantly.aloha.importer.domain.gndline.GndLineImporter;
import net.savantly.aloha.importer.domain.gndrevn.GndRevnImporter;
import net.savantly.aloha.importer.domain.gndsale.GndSaleImporter;
import net.savantly.aloha.importer.domain.gndslsum.GndSlSumImporter;
import net.savantly.aloha.importer.domain.gndtndr.GndTndrImporter;
import net.savantly.aloha.importer.domain.gndvoid.GndVoidImporter;
import net.savantly.aloha.importer.domain.itm.ItemImporter;
import net.savantly.aloha.importer.domain.mod.ModImporter;
import net.savantly.aloha.importer.domain.modcode.ModCodeImporter;
import net.savantly.aloha.importer.domain.odr.OrderModeImporter;
import net.savantly.aloha.importer.domain.pet.PettyCashImporter;
import net.savantly.aloha.importer.domain.pro.PromoImporter;
import net.savantly.aloha.importer.domain.tax.TaxImporter;
import net.savantly.aloha.importer.domain.tdr.TdrImporter;

@Service
public class ImporterBeanResolver {
	
	private final ApplicationContext context;
	
	public ImporterBeanResolver(ApplicationContext context) {
		this.context = context;
	}

	public DbfImporter<? extends ImportIdentifiable, ? extends Serializable> getImporter(AlohaTable table) {
		switch (table) {
		case ADJTIME:
			return this.context.getBean(AdjustedTimeImporter.class);
		case CAT:
			return this.context.getBean(CatImporter.class);
		case CMP:
			return this.context.getBean(CompImporter.class);
		case EMP:
			return this.context.getBean(EmployeeImporter.class);
		case GIF:
			return this.context.getBean(GiftCardImporter.class);
		case GNDADJACK:
			return this.context.getBean(GndAdjAckImporter.class);
		case GNDDEPST:
			return this.context.getBean(GndDepstImporter.class);
		case GNDDRWR:
			return this.context.getBean(GndDrwrImporter.class);
		case GNDITEM:
			return this.context.getBean(GndItemImporter.class);
		case GNDLBSUM:
			return this.context.getBean(GndLbSumImporter.class);
		case GNDLINE:
			return this.context.getBean(GndLineImporter.class);
		case GNDREVN:
			return this.context.getBean(GndRevnImporter.class);
		case GNDSALE:
			return this.context.getBean(GndSaleImporter.class);
		case GNDSLSUM:
			return this.context.getBean(GndSlSumImporter.class);
		case GNDTNDR:
			return this.context.getBean(GndTndrImporter.class);
		case GNDVOID:
			return this.context.getBean(GndVoidImporter.class);
		case ITM:
			return this.context.getBean(ItemImporter.class);
		case MOD:
			return this.context.getBean(ModImporter.class);
		case MODCODE:
			return this.context.getBean(ModCodeImporter.class);
		case ODR:
			return this.context.getBean(OrderModeImporter.class);
		case PET:
			return this.context.getBean(PettyCashImporter.class);
		case PRO:
			return this.context.getBean(PromoImporter.class);
		case TAX:
			return this.context.getBean(TaxImporter.class);
		case TDR:
			return this.context.getBean(TdrImporter.class);
		default:
			throw new RuntimeException("couldn't find importer bean for " + table.name());
		}
	}
}
